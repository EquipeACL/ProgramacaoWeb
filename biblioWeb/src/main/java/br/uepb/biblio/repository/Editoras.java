package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityEditora;

/**
 * Esse é o repositório da classe Editoras, responsável por conter  métodos que tratam a Entidade Editora
 * @author EquipeACL
 *
 */
@Repository
public interface Editoras extends JpaRepository<EntityEditora,Integer> {
	
	public Optional<EntityEditora> findByNomeIgnoreCase(String nome);

}
