package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;

@Component
public class CuocoValidator implements Validator{

	@Autowired
	private CuocoRepository cuocoRepository;
	
	@Override
	public void validate(Object o, Errors errors) {
		Cuoco cuoco= (Cuoco)o;
		if (cuoco.getNome()!=null && cuoco.getCognome()!=null && cuoco.getDataNascita()!=null
				&& cuocoRepository.existsByNomeAndCognomeAndDataNascita(cuoco.getNome(),cuoco.getCognome(),cuoco.getDataNascita())) {
			errors.rejectValue("nome" ,"cuoco.duplicate");
			
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Cuoco.class.equals(aClass);
	}
	
	
}
