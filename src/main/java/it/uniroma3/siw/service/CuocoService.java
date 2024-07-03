package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;

@Service
public class CuocoService {
	
	@Autowired
	CuocoRepository cuocoRepository;
	
	@Transactional
	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}
    
	@Transactional
	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
	}
     
	@Transactional
	public void save(Cuoco cuoco,MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		cuoco.setImmagine(Base64.getEncoder().encodeToString(file.getBytes()));
		cuocoRepository.save(cuoco);
	}
	
	@Transactional
	public void save(Cuoco cuoco) {
		cuocoRepository.save(cuoco);		
	}

	@Transactional
	public List<Cuoco> findByNome(String nome){
		return this.cuocoRepository.findByNome(nome);
	}

	public Cuoco findByNomeAndCognome(String name, String surname) {
		// TODO Auto-generated method stub
		return this.cuocoRepository.findByNomeAndCognome(name,surname);
	}


	
	


}
