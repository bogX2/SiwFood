package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Cuoco;

@Repository
public interface CuocoRepository extends CrudRepository<Cuoco, Long> {
	
	public boolean existsByNomeAndCognomeAndDataNascita(String nome, String cognome, LocalDate dataNascita);

	public List<Cuoco> findByNome(String nome);

	public Cuoco findByNomeAndCognome(String nome, String cognome);


}
