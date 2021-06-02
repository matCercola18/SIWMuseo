package it.uniroma3.siw.spring.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.spring.componenti.Filtro;
import it.uniroma3.siw.spring.componenti.Immagine;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class CollectionController {

	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private Filtro filtro;

	@Autowired
	private CuratoreService curatoreService;
	
	@GetMapping("/addCollection")
	public String addCollection(Model model) {
		model.addAttribute("collezione",new Collezione());
		return "adminCollezioni";
	}
	
	@PostMapping("/addCollection")
	public String saveCollection(@ModelAttribute("collezione") Collezione collezione,Model model) {
		collezioneService.inserisci(collezione);
		return "adminCollezioni";
	}
	
	
	
	@GetMapping("/collezioni")
	public String tutteCollezioni(Model model) {
		model.addAttribute("collezioni",collezioneService.tutte());
		model.addAttribute("filtro", filtro);
		return "collezioni";
	}
	
	@PostMapping("/collezioni")
	public String postTutteCollezioni(@ModelAttribute("filtro") Filtro filtro, Model model) {
		
		List<Collezione> collezioni=new ArrayList<>();
		if(filtro.isOrdinaPerNome())
			collezioni=collezioneService.tutteOrdinateCresc();
		else
			collezioni=collezioneService.tutte();
	
		String ricerca = filtro.getRicerca();
		if(ricerca!=null && !ricerca.equals(""))
			collezioni=collezioneService.getByNome(ricerca);
		
		model.addAttribute("collezioni", collezioni);
		return "collezioni";
	}
	
	@GetMapping("collezione/{id}")
	public String getCollezione(@PathVariable("id") Long id,Model model) {
		Collezione collezione=collezioneService.getById(id);
		List<Opera> opere=collezione.getOpere();
		if(opere.isEmpty())
			System.out.println("Vuota");
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", opere);
		
		return "collezione";
	}
	
	/*Operazioni sulle collezioni dell'admi*/
	
	@GetMapping("/admin/collezioni")
	public String adminCollezioni(Model model){
		model.addAttribute("collezioni", collezioneService.tutte());
		model.addAttribute("filtro", new Filtro());
		
		return "adminCollezioni";
		
	}
	@GetMapping("/admin/cancellaCollezione/{id}")
	public String adminCancellaCollezioni(@PathVariable("id") Long id,Model model){
		collezioneService.deleteById(id);
		model.addAttribute("collezioni", collezioneService.tutte());
		model.addAttribute("filtro", filtro);
		return "adminCollezioni";
		
	}
	
	@GetMapping("/admin/modificaCollezione/{id}")
	public String adminModificaCollezione(@PathVariable("id") Long id,Model model){
		Collezione collezione=collezioneService.getById(id);
		List<Opera> opere=collezione.getOpere();
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", opere);
		//model.addAttribute("filtro", filtro);
		return "modificaCollezione";
		
	}
	@GetMapping("/admin/cancellaOpera/{id}")
	public String cancellaOpera(@PathVariable("id") Long id,Model model) {
		Collezione collezione=operaService.findById(id).getCollezione();
		operaService.deleteById(id);
		
		List<Opera> opere=collezione.getOpere();
		model.addAttribute("collezione",collezione);
		model.addAttribute("opere", opere);
		
		return 	"modificaCollezione";
		
	}
	@PostMapping("/admin/collezioni")
	public String adminCollezioni(@ModelAttribute("filtro") Filtro filtro, Model model) {
		
		List<Collezione> collezioni=new ArrayList<>();
		if(filtro.isOrdinaPerNome())
			collezioni=collezioneService.tutteOrdinateCresc();
		else
			collezioni=collezioneService.tutte();
	
		String ricerca = filtro.getRicerca();
		if(ricerca!=null && !ricerca.equals(""))
			collezioni=collezioneService.getByNome(ricerca);
		
		model.addAttribute("collezioni", collezioni);
		return "adminCollezioni";
	}
	
	@GetMapping("/admin/{id}/aggiungiOpera")
	public String addOperaACollezione(@PathVariable("id") Long id, Model model) {
		Collezione collezione=collezioneService.getById(id);
		List<Artista> autori=artistaService.tutti();
		
		model.addAttribute("collezione", collezione);
		model.addAttribute("autori",autori);
		model.addAttribute("opera", new Opera());
		model.addAttribute("file", new Immagine());
		return "operaForm";
		
	}
	
	@PostMapping("/admin/{id}/aggiungiOpera")
	public String postaddOperaACollezione(@PathVariable("id") Long id, @ModelAttribute("opera") Opera opera,@RequestParam("img") MultipartFile img,Model model) {
		Collezione collezione=collezioneService.getById(id);
		
		
		String nomeFile=opera.getTitolo().replaceAll(" ", "");
		String pathFile="src/main/resources/static/images/"+nomeFile+".jpg";
		File f=new File(pathFile);
		 
		try {
			FileUtils.copyInputStreamToFile(img.getInputStream(), f);
		}
		 catch (Exception e) {
			e.printStackTrace();
		}

	
		opera.setPathImg("/images/"+nomeFile+".jpg");
		opera.setCollezione(collezione);
		List<Opera> opere=collezione.getOpere();
		opere.add(opera);
		collezioneService.inserisci(collezione);
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", collezione.getOpere());
					
		
		return "modificaCollezione";
	}
	
	@GetMapping("/admin/nuovaCollezione")
	public String addCollezione(Model model) {
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("curatori",curatoreService.tutti());
		return "collezioneForm";
	}
	
	@PostMapping("/admin/nuovaCollezione")
	public String postAddCollezione(@ModelAttribute("collezione") Collezione collezione,Model model) {
		collezioneService.inserisci(collezione);
		model.addAttribute("filtro", filtro);
		model.addAttribute("collezioni", collezioneService.tutte());
		return "adminCollezioni";
	}
	
	
}
