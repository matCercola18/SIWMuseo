package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
 public class Artista {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String dataNascita;
	
	private String luogoNascita;
	
	private String dataMorte;
	
	private String luogoMorte;
	
	private String nazionalita;
	
	@Column(length=2000)
	private String biografia;
	
	@OneToMany(mappedBy = "artista")
	private List<Opera> opere;
	
	public Artista() {
		this.opere=new ArrayList<Opera>();
	}
	
	public Artista(String nome,String cognome,String nazionalita) {
		this.nome=nome;
		this.cognome=cognome;
		this.nazionalita=nazionalita;
		this.opere=new ArrayList<>();
	}

	public Artista(String nome, String cognome, String dataNascita, String luogoNascita, String dataMorte,
			String luogoMorte, String nazionalita, String biografia) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.luogoNascita = luogoNascita;
		this.dataMorte = dataMorte;
		this.luogoMorte = luogoMorte;
		this.nazionalita = nazionalita;
		this.biografia = biografia;
		this.opere =new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		Artista that =(Artista)o;

			
		if(that==null||!(this.nome.equals(that.getNome())&&this.getCognome().equals(that.getCognome())))
			return false;
		else
			return true;
	}
	
}
