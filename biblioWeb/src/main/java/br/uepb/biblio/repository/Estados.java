package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uepb.model.jpaEntity.EntityEstado;


/**
 * Esse é o repositório da classe Estados, responsável por conter  métodos que tratam a Entidade Estado
 * @author EquipeACL
 *
 */
public interface Estados extends JpaRepository<EntityEstado,Integer>{
	
}
