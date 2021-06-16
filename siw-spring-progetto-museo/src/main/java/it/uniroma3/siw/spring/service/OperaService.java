package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.repository.OperaRepository;

@Service
public class OperaService {

	@Autowired
	private OperaRepository operaRepository;
	
	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}
	
	@Transactional
	public Opera findById(Long id) {
		return operaRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public List<Opera> tutte(){
		return (List<Opera>) operaRepository.findAll();
	}
	
	@Transactional
	public void deleteById(Long id) {
		operaRepository.deleteById(id);
	}
	
	@Transactional
	public List<Opera> getByTitolo(String titolo){
		return operaRepository.findByTitolo(titolo);
	}

	@Transactional
	public List<Opera> tutteOrdinateCresc() {
		return operaRepository.findAllByOrderByTitoloAsc();
	}

	@Transactional
	public List<Opera> tutteOrdinatePerAnnoCres() {
		return operaRepository.findAllByOrderByAnnoAsc();
	}
	
	@Transactional
	public List<Opera> tutteOrdinatePerAnnoDesc() {
		return operaRepository.findAllByOrderByAnnoDesc();
	}
}
