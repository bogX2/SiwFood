package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.IngredienteValidator;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.LineaIngrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.LineaIngredienteService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class IngredienteController {

	@Autowired
	IngredienteValidator ingredienteValidator;

	@Autowired
	IngredienteService ingredienteService;

	@Autowired
	RicettaService ricettaService;

	@Autowired
	LineaIngredienteService lineaIngredienteService;

	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", this.ingredienteService.findById(id));
		return "ingrediente.html";
	}

	@GetMapping("/ingrediente")
	public String showIngredienti(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "ingredienti.html";
	}


	@PostMapping("/searchIngrediente")
	public String searchIngrediente(Model model, @RequestParam String nome) {
		model.addAttribute("ingredienti", this.ingredienteService.findByNome(nome));
		return "admin/indexIngrediente.html";
	}

	@GetMapping("/admin/formNewIngrediente")
	public String formNewIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "admin/formNewIngrediente.html";
	}

	@GetMapping("/cuoco/formNewIngrediente")
	public String cformNewIngrediente(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		return "cuoco/formNewIngrediente.html";
	}

	@GetMapping("/admin/indexIngrediente")
	public String indexIngrediente(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "admin/indexIngrediente.html";
	}

	@GetMapping("/cuoco/indexIngrediente")
	public String indexIngredienteC(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "cuoco/indexIngrediente.html";
	}


	@GetMapping("/admin/manageIngrediente")
	public String manageIngrediente(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "admin/manageIngrediente.html";
	}

	@GetMapping("/cuoco/manageIngrediente")
	public String cmanageIngrediente(Model model) {
		model.addAttribute("ingredienti", this.ingredienteService.findAll());
		return "cuoco/manageIngrediente.html";
	}

	@PostMapping("/ingrediente")
	public String snewIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente,BindingResult bindingResult, Model model) {

		this.ingredienteValidator.validate(ingrediente, bindingResult);

		if(!bindingResult.hasErrors()) {
			this.ingredienteService.save(ingrediente);
			model.addAttribute("ingrediente", ingrediente);
			return "redirect:ingrediente/"+ingrediente.getId();
		}
		else {

			return "admin/formNewIngrediente.html";
		}
	}

	@PostMapping("/admin/ingredientiToAdd")
	public String addIngrediente(@ModelAttribute("li") LineaIngrediente li, @RequestParam("ricettaId") Long id,
			@RequestParam String nomeIngrediente, Model model) {

		Ricetta ricetta = ricettaService.findById(id);
		Ingrediente ingr= ingredienteService.findByNome(nomeIngrediente).get(0);
		li.setIngrediente(ingr);


		this.lineaIngredienteService.save(li);
		ricetta.getLineeIngredienti().add(li);
		this.ricettaService.save(ricetta);

		List<Ingrediente> ingredientiDaAggiungere = this.ingredienteService.findIngredientiNotInRicetta(id);

		model.addAttribute("ingredienti", ingredientiDaAggiungere);
		model.addAttribute("li", new LineaIngrediente());
		model.addAttribute("ricetta", ricetta);
		return "/admin/ingredientiToAdd.html";
	}

	@PostMapping("/cuoco/ingredientiToAdd")
	public String caddIngrediente(@ModelAttribute("li") LineaIngrediente li, @RequestParam("ricettaId") Long id,
			@RequestParam String nomeIngrediente, Model model) {
		
		Ricetta ricetta = ricettaService.findById(id);
		Ingrediente ingr = ingredienteService.findByNome(nomeIngrediente).get(0);
		li.setIngrediente(ingr);

		this.lineaIngredienteService.save(li);
		ricetta.getLineeIngredienti().add(li);
		this.ricettaService.save(ricetta);
		
		List<Ingrediente> ingredientiDaAggiungere = this.ingredienteService.findIngredientiNotInRicetta(id);

		  model.addAttribute("ingredienti",ingredientiDaAggiungere);
		    model.addAttribute("li", new LineaIngrediente());
		    model.addAttribute("ricetta", ricetta);
		    return "/cuoco/ingredientiToAdd.html";

	

	}
	

}
