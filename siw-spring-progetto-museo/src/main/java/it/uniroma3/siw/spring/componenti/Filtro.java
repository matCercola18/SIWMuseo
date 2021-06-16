package it.uniroma3.siw.spring.componenti;

import org.springframework.stereotype.Component;

import lombok.Data;
/*Classe per realizzare i filtri nelle ricerce*/
@Component
@Data
public class Filtro {
	
	private boolean ordinaPerNome;
	
	private String ordinaPerAnno;
	
	private String ricerca;

	

}
