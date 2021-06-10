package it.uniroma3.siw.spring.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Collezione;

@Component
public class CollezioneValidator  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Collezione.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Collezione collezione=(Collezione) target;
		if(collezione.getCuratore()==null)
			errors.reject("curatoreAssente");
		
	}

}
