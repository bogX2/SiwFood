package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Ingrediente;

@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

	@Query(value = "SELECT * FROM ingrediente i WHERE i.id NOT IN (SELECT ingrediente_id FROM linea_ingrediente WHERE ricetta_id = :ricettaId)", nativeQuery = true)
	public  List<Ingrediente> findIngredientiNotInRicetta(@Param("ricettaId") Long ricettaId);

	public List<Ingrediente> findByNome(String nome);

	public boolean existsByNome(String nome);

}
