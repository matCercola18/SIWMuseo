package it.uniroma3.siw.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional 
	public Credentials getCredentials(Long id) {
		return credentialsRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public Credentials getCredentials(String username) {
		return credentialsRepository.findByUsername(username).orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		return credentialsRepository.save(credentials);
	}
}
