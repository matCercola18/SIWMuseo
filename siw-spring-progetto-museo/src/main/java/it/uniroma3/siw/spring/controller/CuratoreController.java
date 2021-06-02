package it.uniroma3.siw.spring.controller;

import java.util.List;

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

	@GetMapping("/admin/addCuratore")
	public String addCuratore(Model model) {
		
		model.addAttribute("curatore", new Curatore());
		
		return "curatoreForm";
	}

	@PostMapping("/admin/addCuratore")
	public String aggiungiCuratore(@ModelAttribute("curatore") Curatore curatore, Model model) {

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
