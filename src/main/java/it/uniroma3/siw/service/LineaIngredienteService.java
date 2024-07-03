package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.LineaIngrediente;
import it.uniroma3.siw.repository.LineaIngredienteRepository;

@Service
public class LineaIngredienteService {
	
	@Autowired
	LineaIngredienteRepository lineaIngredienteRepository;

	public LineaIngrediente findById(Long id) {
		return lineaIngredienteRepository.findById(id).get();
	}

	public Iterable<LineaIngrediente> findAll() {
		return lineaIngredienteRepository.findAll();
	}
	

	public void save(LineaIngrediente lineaIngrediente) {
		// TODO Auto-generated method stub
		lineaIngredienteRepository.save(lineaIngrediente);
	}

	public LineaIngrediente[] findLineeIngredientiNotInRicetta(Long ricId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
