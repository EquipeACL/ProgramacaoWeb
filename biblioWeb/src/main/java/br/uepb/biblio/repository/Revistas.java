package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityRevista;


/**
 * Esse é o repositório da classe Revistas, responsável por conter  métodos que tratam a Entidade Revista
 * @author EquipeACL
 *
 */
@Repository
public interface Revistas extends JpaRepository<EntityRevista, Integer>{
	public Optional<EntityRevista> findByTituloIgnoreCase(String nome);
}
