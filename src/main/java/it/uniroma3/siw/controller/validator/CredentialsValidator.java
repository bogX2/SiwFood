package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Component
public class CredentialsValidator implements Validator {
	@Autowired
	private CredentialsRepository credentialsRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Credentials credentials=(Credentials) target;
		if(credentials.getUsername()!=null && this.credentialsRepository.existsByUsername(credentials.getUsername())) {
			errors.rejectValue("username", "username.duplicate");
		}
		
	}
	

}
