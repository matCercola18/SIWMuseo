package it.uniroma3.siw.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.componenti.Filtro;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Curatore;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;

@Controller
public class CuratoreController {

	@Autowired
	private CuratoreService curatoreService;

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private Filtro filtro;

	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/admin/addCuratore")
	public String addCuratore(Model model,@ModelAttribute("collezione") Collezione collezione) {
		logger.debug("Admin: Aggiungi curatore");
		model.addAttribute("curatore", new Curatore());
		
		return "curatoreForm";
	}

	@PostMapping("/admin/addCuratore")
	public String aggiungiCuratore(@ModelAttribute("curatore") Curatore curatore,@ModelAttribute("collezione") Collezione collezione, Model model) {

		curatoreService.inserisci(curatore);

		model.addAttribute("filtro", filtro);
		model.addAttribute("collezioni", collezioneService.tutte());
		
		return "adminCollezioni";
	}

	@GetMapping("/admin/curatore/{id}")
	public String getCuratore(@PathVariable("id") Long id, Model model) {
		
		Curatore curatore = curatoreService.getById(id);
		List<Collezione> collezioni = curatore.getCollezioni();

		model.addAttribute("curatore", curatore);
		model.addAttribute("collezioni", collezioni);
		
		return "curatore";
	}
}
