package it.uniroma3.siw.spring.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Opera;

@Component
public class OperaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Opera opera=(Opera) target;
		if(opera.getArtista()==null)
			errors.reject("artistaAssente");
		
	}

}
