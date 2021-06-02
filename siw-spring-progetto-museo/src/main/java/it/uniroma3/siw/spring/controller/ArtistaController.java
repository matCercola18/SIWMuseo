package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.spring.componenti.Filtro;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;

@Controller
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private Filtro filtro;

	@Autowired
	private ArtistaValidator artistaValidator;
	
	@GetMapping("/artista/{id}")
	public String getArtista(@PathVariable("id") Long id,Model model) {
		Artista artista=artistaService.getArtistaById(id);
		List<Opera> opere=artista.getOpere();
		model.addAttribute("artista",artista);
		model.addAttribute("opere", opere);
		return "artista";
	}
	
	@GetMapping("/autori")
	public String getAutori(Model model) {
		List<Artista> artisti=artistaService.tutti();
		model.addAttribute("autori",artisti);
		model.addAttribute("filtro", filtro);
		return "autori";
	}
	
	
	@PostMapping("/autori")
	public String postAutori(@ModelAttribute("filtro") Filtro filtro,Model model) {
		List<Artista> artisti=new ArrayList<>();
		
		if(filtro.isOrdinaPerNome())
			artisti=artistaService.tuttiOrdinatiCresc();
		else
			artisti=artistaService.tutti();
		
		String ricerca=filtro.getRicerca();
		if(ricerca!=null && !ricerca.equals("")) {
			String[] arrayS=ricerca.split(" ");
			if(arrayS.length==1)
				artisti=artistaService.getByNomeOrCognome(arrayS[0]);
			else
				artisti=artistaService.getByNomeOrCognome(arrayS[0],arrayS[1]);
		}
		model.addAttribute("autori",artisti);
		
		return "autori";
	}
	
	
	@GetMapping("/admin/addArtista")
	public String addArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "artistaForm";
	}
	
	
	@PostMapping("/admin/addArtista")
	public String aggiungiArtista(@ModelAttribute("artista") Artista artista,Model model,BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		if(!bindingResult.hasErrors()) {
			artistaService.inserisci(artista);
			
			
			model.addAttribute("collezioni",collezioneService.tutte());
			model.addAttribute("filtro", filtro);
			return "adminCollezioni";
		}
		return "artistaForm";
	}
}
