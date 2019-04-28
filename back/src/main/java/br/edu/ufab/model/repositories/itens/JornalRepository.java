package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.Jornal;

@Repository
public interface JornalRepository extends CrudRepository<Jornal, Long> {
	
	public Optional<Jornal> findByTitulo(String titulo);

}
