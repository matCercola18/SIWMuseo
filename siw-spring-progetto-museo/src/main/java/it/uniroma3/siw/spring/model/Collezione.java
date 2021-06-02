package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data public class Collezione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String descrizione;
	
	@ManyToOne
	private Curatore curatore;
	
	@OneToMany(mappedBy = "collezione", cascade=CascadeType.ALL)
	private List<Opera> opere;
	
	public Collezione() {
		this.opere=new ArrayList<Opera>();
	}

	public Collezione(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.opere=new ArrayList<Opera>();
	}
}
