package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.Revista;

@Repository
public interface RevistaRepository extends CrudRepository<Revista, Long> {
	
	public Optional<Revista> findByTitulo(String titulo);

}
