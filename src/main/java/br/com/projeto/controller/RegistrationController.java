package br.com.projeto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.projeto.dto.RegistrationDTO;
import br.com.projeto.service.RegistrationService;
import br.com.projeto.validator.EmailValidator;
import br.com.projeto.validator.PasswordValidator;

@Controller
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private EmailValidator emailValidator;

	@Autowired
	private PasswordValidator passwordValidator;
	
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(emailValidator, passwordValidator);
	}

	@GetMapping("/registration")
	public ModelAndView registration(final RegistrationDTO registrationUser) {
		ModelAndView mv = new ModelAndView("/registration/add");
		mv.addObject("registrationUser", registrationUser);
		return mv;
	}

	@PostMapping("/registration/save")
	public ModelAndView save(@Valid RegistrationDTO registrationUser, BindingResult result) {
		ModelAndView mv = new ModelAndView("/registration/success");
		if (result.hasErrors()) {
			return registration(registrationUser);
		} else {
			registrationService.registerUser(registrationUser, result);
			mv.addObject("registrationUser", registrationUser);
		}
		return mv;
	}

}
