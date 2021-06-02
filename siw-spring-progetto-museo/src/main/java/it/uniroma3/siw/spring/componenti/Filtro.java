package it.uniroma3.siw.spring.componenti;

import org.springframework.stereotype.Component;

@Component
public class Filtro {
	
	private boolean ordinaPerNome;
	
	private String ricerca;

	public boolean isOrdinaPerNome() {
		return ordinaPerNome;
	}

	public void setOrdinaPerNome(boolean ordinaPerNome) {
		this.ordinaPerNome = ordinaPerNome;
	}

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

}
