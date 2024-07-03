package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {
	
	@Autowired
	RicettaRepository ricettaRepository;
	
	@Transactional
	public Ricetta findById(Long id) {
		return ricettaRepository.findById(id).get();
	}
    
	@Transactional
	public Iterable<Ricetta> findAll() {
		return ricettaRepository.findAll();
	}
	
	@Transactional
	public List<Ricetta> findByCuoco(Cuoco c){
		return this.ricettaRepository.findByCuoco(c);
	}
	
	@Transactional
	public List<Ricetta> findByNome(String nome){
		return this.ricettaRepository.findByNome(nome);
	}
	
	@Transactional
	public void save(Ricetta ricetta, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		ricetta.setImmagine(Base64.getEncoder().encodeToString(file.getBytes()));
		 ricettaRepository.save(ricetta);
	}

	@Transactional
	public void save(Ricetta ricetta) {
		// TODO Auto-generated method stub
		ricettaRepository.save(ricetta);
		
	}


}
