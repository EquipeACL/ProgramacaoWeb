package br.edu.ufab.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.Autor;

@Repository
public interface AutorRepository extends CrudRepository<Autor, Long> {
	
	public Optional<Autor> findByNome(String nome);

}
