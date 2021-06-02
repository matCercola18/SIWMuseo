package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.componenti.Filtro;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class MuseoController {

	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaService operaService;

	@Autowired
	private ArtistaService artistaService;

	@GetMapping("/")
	public String index() {
		return "home";
	}
	
	@GetMapping("/opera")
	public String opera() {
		return "dipinto";
	}
	@GetMapping("/collezione")
	public String collezione() {
		return "collezione";
	}
	
	@GetMapping("/artista")
	public String artista() {
		return "artista";
	}
	
	@GetMapping("/ricerca")
	public String faiRicerca(Model model) {
		model.addAttribute("filtro", new Filtro());
		return "ricerca";
	}
	
	@PostMapping("/ricerca")
	public String risultatiRicerca(@ModelAttribute("filtro") Filtro filtro,Model model) {
		String ricerca=filtro.getRicerca();

		List<Collezione> collezioni=new ArrayList<>();
		List<Opera> opere=new ArrayList<>();
		List<Artista> artisti=new ArrayList<>();
		if(ricerca!=null && !ricerca.equals("")) {
			collezioni=collezioneService.getByNome(ricerca);
			opere=operaService.getByTitolo(ricerca);
			String[] arrayS=ricerca.split(" ");
			if(arrayS.length==1)
				artisti=artistaService.getByNomeOrCognome(arrayS[0]);
			else
				artisti=artistaService.getByNomeOrCognome(arrayS[0],arrayS[1]);
		}
			
		
		model.addAttribute("collezioni", collezioni);
		model.addAttribute("opere", opere);
		model.addAttribute("artisti", artisti);
		
		return "ricerca";
	}
}
