package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.LineaIngrediente;

@Repository
public interface LineaIngredienteRepository extends CrudRepository<LineaIngrediente,Long> {

}
