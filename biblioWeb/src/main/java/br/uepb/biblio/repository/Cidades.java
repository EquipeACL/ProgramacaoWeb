package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityCidade;

@Repository
public interface Cidades extends JpaRepository <EntityCidade,Integer>{
	

}
