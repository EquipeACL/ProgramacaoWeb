package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityRevista;

@Repository
public interface Revistas extends JpaRepository<EntityRevista, Integer>{
	public Optional<EntityRevista> findByTituloIgnoreCase(String nome);
}
