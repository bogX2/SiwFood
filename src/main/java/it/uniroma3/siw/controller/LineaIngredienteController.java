package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.LineaIngredienteService;

@Controller
public class LineaIngredienteController {
	@Autowired
	LineaIngredienteService lineaIngredienteService;
	

}
