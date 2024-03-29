package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.repository.ArtistaRepository;

@Service
public class ArtistaService {

	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public Artista inserisci(Artista artista) {
		return artistaRepository.save(artista);
	}
	
	@Transactional
	public List<Artista> tutti(){
		return (List<Artista>) artistaRepository.findAll();
	}
	
	@Transactional
	public Artista getArtistaById(Long id) {
		return artistaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Artista> tuttiOrdinatiCresc(){
		return artistaRepository.findAllByOrderByCognomeAsc();
	}
	
	@Transactional
	public List<Artista> getByNomeOrCognome(String nome,String cognome){
		return artistaRepository.findByNomeOrCognome(nome, cognome);
	}
	
	@Transactional
	public List<Artista> getByNomeOrCognome(String s){
		return artistaRepository.findByNomeOrCognome(s,s);
	}
	
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> artisti=artistaRepository.findByNomeAndCognome(artista.getNome(), artista.getCognome());
		if(artisti.isEmpty())
			return false;
		else
			return true;
	}

}
