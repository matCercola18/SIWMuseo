package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{

	public List<Opera> findByTitolo(String titolo);
	
	public List<Opera> findAllByOrderByTitoloAsc();

	public List<Opera> findAllByOrderByAnnoAsc();
	
	public List<Opera> findAllByOrderByAnnoDesc();
}
