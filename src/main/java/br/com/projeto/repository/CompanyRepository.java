package br.com.projeto.repository;

import br.com.projeto.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByName(String name);

        public Company findById(Long id);

        public void deleteById(Long id);
	
}
