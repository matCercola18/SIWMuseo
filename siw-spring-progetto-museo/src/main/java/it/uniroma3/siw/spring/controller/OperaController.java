package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import it.uniroma3.siw.spring.service.OperaService;

@Controller
public class OperaController {

	@Autowired
	private OperaService operaService;

	@Autowired
	private Filtro filtro;

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private ArtistaService artistaService;

	@Autowired
	private OperaValidator operaValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* Visualizzare un'opera */
	@GetMapping("/opera/{id}")
	public String getOpera(@PathVariable("id") Long id, Model model) {

		Opera opera = operaService.findById(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artista", opera.getArtista());

		return "opera";
	}

	/* Visualizzare tutte le opere */
	@GetMapping("/opere")
	public String tutteOpere(Model model) {

		model.addAttribute("opere", operaService.tutte());
		model.addAttribute("filtro", filtro);

		return "opere";
	}

	@PostMapping("/opere")
	public String postTutteOpere(@ModelAttribute("filtro") Filtro filtro, Model model) {

		List<Opera> opere = new ArrayList<>();
		
		if (filtro.isOrdinaPerNome())
			opere = operaService.tutteOrdinateCresc();
		else if (filtro.getOrdinaPerAnno() != null) {
			
			if (filtro.getOrdinaPerAnno().equals("cres"))
				opere = operaService.tutteOrdinatePerAnnoCres();
			else if (filtro.getOrdinaPerAnno().equals("desc"))
				opere = operaService.tutteOrdinatePerAnnoDesc();
			
		} else
			opere = operaService.tutte();

		String ricerca = filtro.getRicerca();
		if (ricerca != null && !ricerca.equals(""))
			opere = operaService.getByTitolo(ricerca);

		model.addAttribute("opere", opere);
		
		return "opere";
	}

	@GetMapping("/admin/cancellaOpera/{id}")
	public String cancellaOpera(@PathVariable("id") Long id, Model model) {
		
		logger.debug("Admin: Cancella opera");
		Collezione collezione = operaService.findById(id).getCollezione();
		operaService.deleteById(id);

		List<Opera> opere = collezione.getOpere();
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", opere);

		return "modificaCollezione";

	}

	@GetMapping("/admin/{id}/aggiungiOpera")
	public String addOperaACollezione(@PathVariable("id") Long id, Model model) {
		
		logger.debug("Admin: Crea opera e aggiungi a collezione");
		Collezione collezione = collezioneService.getById(id);
		List<Artista> autori = artistaService.tutti();

		model.addAttribute("collezione", collezione);
		model.addAttribute("autori", autori);
		model.addAttribute("opera", new Opera());
		model.addAttribute("file", new Immagine());
		
		return "operaForm";

	}

	@PostMapping("/admin/{id}/aggiungiOpera")
	public String postaddOperaACollezione(@PathVariable("id") Long id, @ModelAttribute("opera") Opera opera,
			@RequestParam("img") MultipartFile img, Model model, BindingResult bindingResult) {
		
		Collezione collezione = collezioneService.getById(id);
		this.operaValidator.validate(opera, bindingResult);
		if (bindingResult.hasErrors()) {
			List<Artista> autori = artistaService.tutti();
			model.addAttribute("collezione", collezione);
			model.addAttribute("autori", autori);

			return "operaForm";
		}

		try {

			opera.setImgByte(img.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		opera.setCollezione(collezione);
		List<Opera> opere = collezione.getOpere();
		opere.add(opera);
		collezioneService.inserisci(collezione);
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", collezione.getOpere());

		return "modificaCollezione";
	}

	@GetMapping("/admin/confermaCancellazione/Opera/{id}")
	public String getPaginaConfermaCancellaOpera(Model model, @PathVariable("id") Long id) {
		
		Opera opera = operaService.findById(id);
		model.addAttribute("opera", opera);
		model.addAttribute("collezione", opera.getCollezione());
		
		return "confermaOpera";
	}

}
