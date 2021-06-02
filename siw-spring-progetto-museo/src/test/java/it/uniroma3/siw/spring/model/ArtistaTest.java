package it.uniroma3.siw.spring.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class ArtistaTest {
	@Test
	void testArtistaEqualsSameFields() {
		Artista uno=new Artista("Mario","Rossi","italiano");
		Artista due=new Artista("Mario","Rossi","italiano");
		assertEquals(uno,due);
	}
	
	@Test
	void testArtistaEqualsSameNameAndLastNameDifferentNazionalita() {
		Artista uno=new Artista("Mario","Rossi","italiano");
		Artista due=new Artista("Mario","Rossi","americano");
		assertNotEquals(uno,due);
	}
	
	@Test
	void testArtistaEqualsDifferentsFiels() {
		Artista uno=new Artista("Mario","Rossi","18 settembre 1890","Roma","18 settembre 2021","Roma","italiano","italiano");
		Artista due=new Artista("Mario","Rossi","18 settembre 1690","Roma","18 settembre 1780","Roma","italiano","italiano");
		assertNotEquals(uno,due);
	}

}
