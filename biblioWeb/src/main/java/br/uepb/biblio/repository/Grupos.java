package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Grupo;

/**
 * Esse é o repositório da classe Grupos, responsável por conter  métodos que tratam a Entidade Grupo
 * @author EquipeACL
 *
 */
@Repository
public interface Grupos extends JpaRepository <Grupo,Integer> {
	
	Optional <Grupo> findByNomeIgnoreCase(String nome);

}
