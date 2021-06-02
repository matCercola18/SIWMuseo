package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data 
	public class Curatore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long matricola;
	
	private String nome;
	    
	private String cognome;
	
	private String dataNascita;
	
	private String luogoNascita;
	
	private String email;
	
	private String telefono;
	
	
	@OneToMany(mappedBy = "curatore")
	private List<Collezione> collezioni;
	
	public Curatore() {
		this.collezioni=new ArrayList<Collezione>();
	}

	public Curatore(String nome, String cognome, String dataNascita, String luogoNascita, String email, String telefono) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.email = email;
		this.telefono = telefono;
		this.collezioni=new ArrayList<Collezione>();
	}
	
	public Curatore(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.collezioni=new ArrayList<Collezione>();
	}
	
	
}
