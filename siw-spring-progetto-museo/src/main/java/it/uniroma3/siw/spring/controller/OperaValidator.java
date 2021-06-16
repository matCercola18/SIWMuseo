package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Opera;

@Component
public class OperaValidator implements Validator{

	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Opera.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Opera opera=(Opera) target;
		if(opera.getArtista()==null) {
			logger.debug("Errore: artista assente");
			errors.reject("artistaAssente");
		}
			
		
	}

}
