package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityOrientador;

/**
 * Esse é o repositório da classe Orientadores, responsável por conter  métodos que tratam a Entidade Orientador
 * @author EquipeACL
 *
 */
@Repository
public interface Orientadores extends JpaRepository <EntityOrientador,Integer>{
	
	Optional <EntityOrientador> findByNomeIgnoreCase(String nome);

}
