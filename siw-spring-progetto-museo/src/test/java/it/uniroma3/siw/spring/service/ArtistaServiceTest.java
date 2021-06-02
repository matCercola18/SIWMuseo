package it.uniroma3.siw.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.uniroma3.siw.spring.model.Artista;

@SpringBootTest
class ArtistaServiceTest {

	@Autowired
	private ArtistaService artistaService;
	
	@BeforeEach
	void setUp() throws Exception {

	}

//	Artista artista=new Artista("Jackson","Pollock","")
	@Test
	void testAlreadyExistFalse() {
		Artista artista=new Artista("Artista","Di prova","italiana");
		
		assertFalse(artistaService.alreadyExists(artista));
	}

	@Test
	void testAlreadyNomeEsistente() {
		Artista artista=new Artista("Jackson","Di prova","italiana");
		
		assertFalse(artistaService.alreadyExists(artista));
	}
	
	
	@Test
	public void testInserisci() {
		Artista artista=new Artista("Artista","Di prova","italiana");
		assertFalse(artistaService.alreadyExists(artista));
		artista=artistaService.inserisci(artista);
		assertTrue(artistaService.alreadyExists(artista));
		assertNotEquals(1,artista.getId());
	}
	
	@Test
	public void testInserisciVuoto() {
		Artista artista=new Artista();
		assertFalse(artistaService.alreadyExists(artista));
		artista=artistaService.inserisci(artista);
		assertTrue(artistaService.alreadyExists(artista));
		assertNotEquals(1,artista.getId());
	}

	
}
