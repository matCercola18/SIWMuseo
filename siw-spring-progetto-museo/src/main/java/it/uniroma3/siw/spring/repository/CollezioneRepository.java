package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Collezione;

public interface CollezioneRepository extends CrudRepository<Collezione,Long>{

	public List<Collezione> findAllByOrderByNomeAsc();
	
	public List<Collezione> findByNome(String nome);
}
