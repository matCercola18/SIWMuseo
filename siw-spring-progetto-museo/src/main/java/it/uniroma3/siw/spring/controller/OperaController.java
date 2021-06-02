package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;
	
	@GetMapping("/opera/{id}")
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera =operaService.findById(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artista", opera.getArtista());
		return "opera";
	}
	
	
}
