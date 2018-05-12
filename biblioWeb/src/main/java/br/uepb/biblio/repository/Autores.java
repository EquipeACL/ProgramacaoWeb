package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityAutor;

@Repository
public interface Autores extends JpaRepository<EntityAutor,Integer> {

	public Optional <EntityAutor> findByNomeIgnoreCase(String nome);
}
