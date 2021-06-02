package it.uniroma3.siw.spring.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.uniroma3.siw.spring.service.CuratoreService;

@SpringBootTest
public class CuratoreTest {

	@Autowired
	private CuratoreService curatoreService;

	@Test
	public void testSaveCuratore() {
		Curatore curatore=new Curatore("Curatore di prova","prova");
		curatoreService.inserisci(curatore);
	}

}
