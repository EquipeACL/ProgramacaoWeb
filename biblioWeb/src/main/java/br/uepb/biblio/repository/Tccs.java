package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityTcc;


/**
 * Esse é o repositório da classe Tccs, responsável por conter  métodos que tratam a Entidade Tcc
 * @author EquipeACL
 *
 */
@Repository
public interface Tccs extends JpaRepository<EntityTcc, Integer>{
	public Optional<EntityTcc> findByTituloIgnoreCase(String nome);
}
