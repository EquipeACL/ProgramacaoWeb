package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uepb.model.jpaEntity.EntityEstado;

public interface Estados extends JpaRepository<EntityEstado,Integer>{
	
}
