package it.uniroma3.siw.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.repository.CollezioneRepository;

@Service
public class CollezioneService {

	@Autowired
	private CollezioneRepository collezioneRepository;

	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return collezioneRepository.save(collezione);
	}

	@Transactional
	public List<Collezione> tutte() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	@Transactional
	public Collezione getById(Long id) {
		return collezioneRepository.findById(id).orElse(null);
	}

	@Transactional
	public List<Collezione> getByNome(String nome) {
		return collezioneRepository.findByNome(nome);
	}

	@Transactional
	public List<Collezione> tutteOrdinateCresc() {
		return collezioneRepository.findAllByOrderByNomeAsc();
	}

	@Transactional
	public void deleteById(Long id) {
		collezioneRepository.deleteById(id);
	}
}
