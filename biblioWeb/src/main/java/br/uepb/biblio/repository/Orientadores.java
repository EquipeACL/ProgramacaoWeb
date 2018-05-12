package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityOrientador;

@Repository
public interface Orientadores extends JpaRepository <EntityOrientador,Integer>{
	
	Optional <EntityOrientador> findByNomeIgnoreCase(String nome);

}
