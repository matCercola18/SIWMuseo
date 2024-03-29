package it.uniroma3.siw.spring.model;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data public class Opera {

	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id; 
	 
	private String titolo; 
	
	private String anno; 
	
	@Column(length = 2000) 
	private String descrizione;  
	 
	private byte[] imgByte;   
	
	@ManyToOne 
	private Collezione collezione;
	
	@ManyToOne
	private Artista artista;
	
	public Opera() {
		
	}

	public Opera(String titolo, String anno, String descrizione) { 
		this.titolo = titolo;
		this.anno = anno;
		this.descrizione = descrizione;
	}
	
	public String getStringImg() {
		return Base64.getEncoder().encodeToString(this.imgByte);
	}
	
}
