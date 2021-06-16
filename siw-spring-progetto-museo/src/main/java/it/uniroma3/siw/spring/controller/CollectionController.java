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

import it.uniroma3.siw.spring.componenti.Filtro;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Opera;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;

@Controller
public class CollectionController {

	@Autowired
	private CollezioneService collezioneService;

	@Autowired
	private Filtro filtro;

	@Autowired
	private CuratoreService curatoreService;

	@Autowired
	private CollezioneValidator collezioneValidator;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* Visualizzare tutte le collezioni */
	@GetMapping("/collezioni")
	public String tutteCollezioni(Model model) {

		model.addAttribute("collezioni", collezioneService.tutte());
		model.addAttribute("filtro", filtro);

		return "collezioni";
	}

	@PostMapping("/collezioni")
	public String postTutteCollezioni(@ModelAttribute("filtro") Filtro filtro, Model model) {

		List<Collezione> collezioni = new ArrayList<>();
		if (filtro.isOrdinaPerNome())
			collezioni = collezioneService.tutteOrdinateCresc();
		else
			collezioni = collezioneService.tutte();

		String ricerca = filtro.getRicerca();
		if (ricerca != null && !ricerca.equals(""))
			collezioni = collezioneService.getByNome(ricerca);

		model.addAttribute("collezioni", collezioni);
		return "collezioni";
	}

	/* Visualizzare una collezione */
	@GetMapping("collezione/{id}")
	public String getCollezione(@PathVariable("id") Long id, Model model) {

		Collezione collezione = collezioneService.getById(id);
		List<Opera> opere = collezione.getOpere();

		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", opere);

		return "collezione";
	}

	/* Operazioni sulle collezioni dell'admin */

	@GetMapping("/admin/collezioni")
	public String adminCollezioni(Model model) {

		logger.debug("Admin: accesso al home delle collezioni");
		model.addAttribute("collezioni", collezioneService.tutte());
		model.addAttribute("filtro", new Filtro());

		return "adminCollezioni";

	}

	@PostMapping("/admin/collezioni")
	public String adminCollezioni(@ModelAttribute("filtro") Filtro filtro, Model model) {

		List<Collezione> collezioni = new ArrayList<>();
		if (filtro.isOrdinaPerNome())
			collezioni = collezioneService.tutteOrdinateCresc();
		else
			collezioni = collezioneService.tutte();

		String ricerca = filtro.getRicerca();
		if (ricerca != null && !ricerca.equals(""))
			collezioni = collezioneService.getByNome(ricerca);

		model.addAttribute("collezioni", collezioni);

		return "adminCollezioni";
	}

	@GetMapping("/admin/cancellaCollezione/{id}")
	public String adminCancellaCollezioni(@PathVariable("id") Long id, Model model) {

		logger.debug("Admin: Cancella collezione");
		collezioneService.deleteById(id);
		model.addAttribute("collezioni", collezioneService.tutte());
		model.addAttribute("filtro", filtro);

		return "adminCollezioni";

	}

	@GetMapping("/admin/modificaCollezione/{id}")
	public String adminModificaCollezione(@PathVariable("id") Long id, Model model) {

		logger.debug("Admin: Modifica collezione");
		Collezione collezione = collezioneService.getById(id);
		List<Opera> opere = collezione.getOpere();
		model.addAttribute("collezione", collezione);
		model.addAttribute("opere", opere);

		return "modificaCollezione";

	}

	@GetMapping("/admin/nuovaCollezione")
	public String addCollezione(Model model) {

		logger.debug("Admin: Crea collezione");
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("curatori", curatoreService.tutti());

		return "collezioneForm";
	}

	@PostMapping("/admin/nuovaCollezione")
	public String postAddCollezione(@ModelAttribute("collezione") Collezione collezione, Model model,
			BindingResult bindingResult) {

		this.collezioneValidator.validate(collezione, bindingResult);
		if (!bindingResult.hasErrors()) {
			collezioneService.inserisci(collezione);
			model.addAttribute("filtro", filtro);
			model.addAttribute("collezioni", collezioneService.tutte());

			return "adminCollezioni";
		}
		model.addAttribute("curatori", curatoreService.tutti());

		return "collezioneForm";
	}

	@GetMapping("/admin/confermaCancellazione/Collezione/{id}")
	public String getPaginaConfermaCancellaCollezione(Model model, @PathVariable("id") Long id) {

		model.addAttribute("collezione", collezioneService.getById(id));

		return "confermaCollezione";
	}

}
