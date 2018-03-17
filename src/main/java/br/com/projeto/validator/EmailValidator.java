package br.com.projeto.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.projeto.dto.RegistrationDTO;
import br.com.projeto.repository.UserRepository;

@Component
public class EmailValidator implements Validator {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationDTO registration = (RegistrationDTO)target;
		if(!errors.hasFieldErrors("email")){
			Integer countByEmail = userRepository.countByEmail(registration.getEmail());
			if(countByEmail != 0){
				errors.rejectValue("email", "registration.email.not.unique");
			}
		}
	}

}
