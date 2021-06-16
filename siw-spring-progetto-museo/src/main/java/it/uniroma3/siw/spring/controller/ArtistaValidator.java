package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator{

	
	@Autowired
	private ArtistaService artistaService;
	
	private static final Logger logger = LoggerFactory.getLogger(ArtistaValidator.class);
	
	

	@Override
	public void validate(Object target, Errors errors) {
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");
		
		if(!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(artistaService.alreadyExists((Artista)target)) {
				logger.debug("duplicato");
				errors.reject("duplicato");
			}
					
		}
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.equals(clazz);
	}

}
