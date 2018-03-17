package br.com.projeto.controller;

import br.com.projeto.exception.CityNotFoundException;
import br.com.projeto.exception.ValidationException;
import br.com.projeto.model.City;
import br.com.projeto.model.Company;
import br.com.projeto.repository.CityRepository;
import br.com.projeto.repository.CompanyRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CompanyController {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CityRepository cityRepository;
        
        private static final String UPLOADED_FOLDER = System.getProperty("user.dir") 
            + "/images/upload/company/";                
        
	@GetMapping(path = { "/company", "/company/index" })
	public ModelAndView findAll() {
                ModelAndView mv = new ModelAndView("/company/index");
                mv.addObject("companies", companyRepository.findAll());

                return mv;
	}

	@GetMapping("/company/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		companyRepository.deleteById(id);
                
		ModelAndView mv = new ModelAndView("redirect:/company/index");
		attributes.addFlashAttribute("successMessage", "Empresa excluída com sucesso.");
		return mv;
	}

	@GetMapping("/company/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
                Company company = companyRepository.findById(id).get();
               
                ModelAndView mv = new ModelAndView("company/edit");
                mv.addObject("company", company);
                mv.addObject("listFederalUnit", getListFederalUnit());
                return mv;
	}
        
	@GetMapping("/company/add")
	public ModelAndView add(Company company) {
                ModelAndView mv = new ModelAndView("company/add");
                mv.addObject("company", company);
                mv.addObject("listFederalUnit", getListFederalUnit());
                return mv;
	}
        
        private List<String> getListFederalUnit(){
                List<City> listCities = cityRepository.findAll();
                
                List<String> listFederalUnit = new ArrayList<>();                
                for (City city : listCities) {
                        if(!listFederalUnit.contains(city.getFederalUnit().toUpperCase())){
                                listFederalUnit.add(city.getFederalUnit().toUpperCase());
                        }
                }
                
                Collections.sort(listFederalUnit);   
                
                return listFederalUnit;
        }        

	@PostMapping("/company/save")
	public ModelAndView save(@Valid Company company,
            BindingResult result,
            @RequestParam("federalUnit") String federalUnit,
            @RequestParam(name = "citySelected", required = false) String citySelected,
            @RequestParam("logoSelected") MultipartFile logoSelected,              
            RedirectAttributes attributes) 
        {
                ModelAndView mv = new ModelAndView();                
                
                try{
                        if (result.hasErrors()) {
                            throw new ValidationException();                                
                        }
                
                        Integer idCompany = company.getId();                        
                        if(idCompany != null){
                            Company companyOld = companyRepository.findById(company.getId()).get();
                            company.setLogo(companyOld.getLogo());
                            
                            if(citySelected == null){                            
                                company.setCity(companyOld.getCity());    
                                throw new CityNotFoundException("Deve-se selecionar uma cidade");
                            }                            
                        }
                        
                        City city = cityRepository.findByName(citySelected);
                        company.setCity(city);
                                        
                        byte[] byteLogo = logoSelected.getBytes();
                        if(byteLogo.length > 0){
                            company.setLogo(BlobProxy.generateProxy(byteLogo));
                        }
                        
                        companyRepository.save(company);   
                        
                        attributes.addFlashAttribute("successMessage", "Empresa salva com sucesso");    
                        mv.setViewName("redirect:/company/index");
                }catch(IOException | CityNotFoundException ex){
                        if(company.getId() == null){ 
                            mv.setViewName("/company/add");
                        }else{
                            mv.setViewName("/company/edit");
                        }
                        mv.addObject("errorMessage", ex.getMessage());                        
                        mv.addObject("listFederalUnit", getListFederalUnit());
                        mv.addObject("ufSelected", federalUnit);
                        mv.addObject("citySelected", citySelected);                        
                } catch(ValidationException ex){
                        if(company.getId() == null){ 
                            mv.setViewName("/company/add");
                        }else{
                            mv.setViewName("/company/edit");
                        }
                        mv.addObject("company", company);
                        mv.addObject("listFederalUnit", getListFederalUnit());
                }
		
		return mv;
	}
        
        
	@GetMapping("/company/listCities/{federalUnit}")
        @ResponseBody
	public List<String> getListCities(@PathVariable("federalUnit") String federalUnit) {
                List<City> listCities = cityRepository.findByFederalUnit(federalUnit);
                
                List<String> listNameCities = new ArrayList<>();

                for (City city : listCities) {
                        if(!listNameCities.contains(city.getName())){
                            listNameCities.add(city.getName());
                        }
                }
                
                return listNameCities;
	}
        
	@GetMapping("/company/logo/{companyId}")
        @ResponseBody
	public ResponseEntity<InputStreamResource> getLogo(@PathVariable("companyId") Integer companyId) {
            try {
                Company company = companyRepository.findById(companyId).get();
                Blob logo = company.getLogo();
                
                String filename = "inline;filename=\"" + company.getName() + "\"";
                
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", filename);
                
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(logo.length())
                        .contentType(MediaType.parseMediaType(getContentType(logo)))
                        .body(new InputStreamResource(logo.getBinaryStream()));                
            } catch (SQLException ex) {
                Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
	}

        private String getContentType(Blob logo) {
            
                String contentType = "";           
                
                try {
                    int logoLength;
                    logoLength = (int) logo.length();
                    
                    byte[] logobAsBytes = logo.getBytes(1, logoLength);

                    ByteArrayInputStream is = new ByteArrayInputStream(logobAsBytes);
                    contentType = URLConnection.guessContentTypeFromStream(is);
                  
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
                }

                return contentType;
        }
}
