package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityAutor;

/**
 * Esse é o repositório da classe Autores, responsável por conter  métodos que tratam a Entidade Autor
 * @author EquipeACL
 *
 */
@Repository
public interface Autores extends JpaRepository<EntityAutor,Integer> {

	public Optional <EntityAutor> findByNomeIgnoreCase(String nome);
}
