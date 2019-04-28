package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.Anais;

@Repository
public interface AnaisRepository extends CrudRepository<Anais, Long> {
	
	public Optional<Anais> findByTitulo(String titulo);

}
