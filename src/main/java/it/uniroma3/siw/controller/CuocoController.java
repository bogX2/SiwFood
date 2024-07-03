package it.uniroma3.siw.controller;
import java.io.IOException;

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

import it.uniroma3.siw.controller.validator.CuocoValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class CuocoController {

	@Autowired
	CuocoValidator cuocoValidator;

	@Autowired
	CuocoService cuocoService;

	@Autowired
	RicettaService ricettaService;

	@GetMapping("/cuoco/{id}")
	public String getCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		model.addAttribute("ricette", this.ricettaService.findByCuoco(this.cuocoService.findById(id)));

		return "cuoco.html";
	}

	@GetMapping("/cuoco")
	public String showCuoco(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "cuochi.html";
	}

	@GetMapping("/admin/formNewCuoco")
	public String formNewCuoco(Model model) {
		model.addAttribute("cuoco", new Cuoco());
		return "admin/formNewCuoco.html";
	}

	@GetMapping("/admin/indexCuoco")
	public String indexCuoco(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "admin/indexCuoco.html";
	}


	@GetMapping("/admin/manageCuochi")
	public String manageCuochi(Model model) {
		model.addAttribute("cuochi", this.cuocoService.findAll());
		return "admin/manageCuochi.html";
	}

	@GetMapping("/admin/formUpdateCuoco/{id}")
	public String formUpdateCuoco(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", cuocoService.findById(id));
		return "admin/formUpdateCuoco.html";
	}



	@PostMapping("/searchCuoco")
	public String searchCuoco(Model model, @RequestParam String nome) {
		model.addAttribute("cuochi", this.cuocoService.findByNome(nome));
		return "admin/indexCuoco.html";
	}


	@PostMapping("/cuoco")
	public String newCuoco(@Valid @ModelAttribute("cuoco") Cuoco cuoco,BindingResult bindingResult, Model model,@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
			this.cuocoService.save(cuoco,imageFile);
			model.addAttribute("cuoco",cuoco);
			return "redirect:cuoco/"+cuoco.getId();
		}
		
	}
