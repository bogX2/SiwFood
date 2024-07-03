package it.uniroma3.siw.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;

@Repository
public interface RicettaRepository extends CrudRepository<Ricetta, Long> {
	
 public List <Ricetta> findByCuoco(Cuoco cuoco);
 
public List<Ricetta> findByNome(String nome);


public boolean existsByNome(String nome);

public boolean existsByNomeAndCuoco(String nome, Cuoco cuoco);

}
