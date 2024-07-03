package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.LineaIngrediente;
import it.uniroma3.siw.service.LineaIngredienteService;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RicettaController {

	@Autowired
	RicettaValidator ricettaValidator;

	@Autowired
	RicettaService ricettaService;

	@Autowired 
	CredentialsService credentialsService;

	@Autowired
	LineaIngredienteService lineaIngredienteService ;

	@Autowired
	IngredienteService IngredienteService ;

	@Autowired
	CuocoService cuocoService;

	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", this.ricettaService.findById(id));
		model.addAttribute("ingredienti", this.ricettaService.findById(id).getLineeIngredienti());
		return "ricetta.html";
	}

	@GetMapping("/ricetta")
	public String showRicetta(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "ricette.html";
	}



	@GetMapping("/admin/indexRicetta")
	public String indexRicetta(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "admin/indexRicetta.html";
	}

	@GetMapping("/cuoco/{id}/indexRicetta")
	public String indexRicetta2(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		model.addAttribute("ricette", this.ricettaService.findByCuoco(this.cuocoService.findById(id)));
		return "cuoco/indexRicetta.html";
	}


	@GetMapping("/admin/manageRicette")
	public String manageRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "admin/manageRicette.html";
	}

	@GetMapping("/cuoco/manageRicette")
	public String cmanageRicette(Model model) {
		model.addAttribute("ricette", this.ricettaService.findAll());
		return "cuoco/manageRicette.html";
	}

	@GetMapping("/admin/formUpdateRicetta/{id}")
	public String formUpdateRicerca(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaService.findById(id));
		return "admin/formUpdateRicetta.html";
	}

	@GetMapping("/cuoco/formUpdateRicetta/{id}")
	public String cformUpdateRicerca(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaService.findById(id));
		return "cuoco/formUpdateRicetta.html";
	}

	@GetMapping("/admin/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		model.addAttribute("cuochi", this.cuocoService.findAll());

		return "admin/formNewRicetta.html";
	}

	@GetMapping("/cuoco/{id}/formNewRicetta")
	public String cformNewRicetta(@PathVariable("id") Long id,Model model) {
		model.addAttribute("cuoco", this.cuocoService.findById(id));
		model.addAttribute("ricetta", new Ricetta());
		return "cuoco/formNewRicetta.html";
	}

	@GetMapping("/admin/setCuocoToRicetta/{cuocoId}/{ricettaId}")
	public String setCuocoToRicetta(@PathVariable("cuocoId") Long cuocoId, @PathVariable("ricettaId") Long ricettaId, Model model) {

		Cuoco cuoco = this.cuocoService.findById(cuocoId);
		Ricetta ricetta = this.ricettaService.findById(ricettaId);
		ricetta.setCuoco(cuoco);
		this.ricettaService.save(ricetta);

		model.addAttribute("ricetta",ricetta);
		return "admin/formUpdateRicetta.html";
	}

	@GetMapping("/admin/addCuoco/{ricId}")
	public String addCuoco(@PathVariable("ricId") Long id, Model model) {
		model.addAttribute("cuochi", cuocoService.findAll());
		model.addAttribute("ricetta",ricettaService.findById(id));
		return "admin/cuochiToAdd.html";
	}

	@GetMapping("/admin/addIngredienti/{ricId}")
	public String addIngredienti(@PathVariable("ricId") Long id, Model model) {
		Ricetta ricetta = ricettaService.findById(id);

		List<Ingrediente> ingredientiDaAggiungere = this.IngredienteService.findIngredientiNotInRicetta(id);

		model.addAttribute("ingredienti", ingredientiDaAggiungere);
		model.addAttribute("li", new LineaIngrediente());
		model.addAttribute("ricetta", ricetta);

		return "/admin/ingredientiToAdd.html";
	}

	@GetMapping("/cuoco/addIngredienti/{ricId}")
	public String caddIngredienti(@PathVariable("ricId") Long id, Model model) {
		Ricetta ricetta = ricettaService.findById(id);

		List<Ingrediente> ingredientiDaAggiungere = this.IngredienteService.findIngredientiNotInRicetta(id);

		model.addAttribute("ingredienti", ingredientiDaAggiungere);
		model.addAttribute("li", new LineaIngrediente());
		model.addAttribute("ricetta", ricetta);

		return "/cuoco/ingredientiToAdd.html";

	}


	@GetMapping("/admin/removeIngredienti/{liId}/{ricId}")
	public String removeIngredienti(@PathVariable("liId") Long liId,@PathVariable("ricId") Long ricId, Model model) {
		Ricetta ric = this.ricettaService.findById(ricId);
		LineaIngrediente li = this.lineaIngredienteService.findById(liId);
		List<LineaIngrediente> lis= ric.getLineeIngredienti();
		lis.remove(li);

		this.ricettaService.save(ric);

		model.addAttribute("ricetta",ricettaService.findById(ricId));
		model.addAttribute("ingredienti", this.IngredienteService.findAll());

		return "admin/formUpdateRicetta.html";
	}

	@GetMapping("/cuoco/removeIngredienti/{liId}/{ricId}")
	public String cremoveIngredienti(@PathVariable("liId") Long liId,@PathVariable("ricId") Long ricId, Model model) {
		Ricetta ric = this.ricettaService.findById(ricId);
		LineaIngrediente li = this.lineaIngredienteService.findById(liId);
		List<LineaIngrediente> lis= ric.getLineeIngredienti();
		lis.remove(li);

		this.ricettaService.save(ric);

		model.addAttribute("ricetta",ricettaService.findById(ricId));
		model.addAttribute("ingredienti", this.IngredienteService.findAll());


		return "cuoco/formUpdateRicetta.html";
	}

	@PostMapping("/searchRicetta")
	public String searchRicetta(Model model, @RequestParam String nome) {
		model.addAttribute("ricette", this.ricettaService.findByNome(nome));
		return "admin/indexRicetta.html";
	}

	@PostMapping("/ricetta")
	public String newRicetta(  @ModelAttribute("ricetta") Ricetta ricetta,Model model,@RequestParam("imageFile") MultipartFile imageFile
			,@RequestParam("IdCuoco") Long idC) throws IOException  {

		Cuoco c= this.cuocoService.findById(idC);
		ricetta.setCuoco(c);
		this.ricettaService.save(ricetta, imageFile);
		c.getRicette().add(ricetta);
		cuocoService.save(c);
		model.addAttribute("ricetta", ricetta);
		return "redirect:ricetta/"+ricetta.getId();

	}

	@PostMapping("/ricettaCuoco")
	public String cnewRicetta(  @ModelAttribute("ricetta") Ricetta ricetta, @ModelAttribute("userDetails") UserDetails userDetails,Model model,@RequestParam("imageFile") MultipartFile imageFile) throws IOException  {
		// this.ricettaValidator.validate(ricetta, bindingResult);
		// if(!bindingResult.hasErrors()) {
		Cuoco cuoco=credentialsService.getCredentials(userDetails.getUsername()).getUser().getCuoco();
		ricetta.setCuoco(cuoco);
		this.ricettaService.save(ricetta,imageFile);
		model.addAttribute("ricetta", ricetta);
		return "redirect:ricetta/"+ricetta.getId();
		//}
		// else {
		// 	return "admin/formNewRicetta";
		//}
	}

}
