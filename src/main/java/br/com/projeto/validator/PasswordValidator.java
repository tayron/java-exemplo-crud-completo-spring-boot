package br.com.projeto.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.projeto.dto.RegistrationDTO;

@Component
public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RegistrationDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegistrationDTO registration = (RegistrationDTO)target;
		if(!errors.hasFieldErrors("password") && !errors.hasFieldErrors("passwordConfirmation")){
			if(!registration.getPassword().equals(registration.getPasswordConfirmation())){
				errors.rejectValue("passwordConfirmation", "registration.password.confirmation.not.match");
			}
		}
	}
}
