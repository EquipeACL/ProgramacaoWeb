package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Permissao;

/**
 * Esse é o repositório da classe Permissoes, responsável por conter  métodos que tratam a Entidade Permissao
 * @author EquipeACL
 *
 */
@Repository
public interface Permissoes  extends JpaRepository<Permissao,Integer>{
	
	Optional <Permissao> findByNomeIgnoreCase(String nome);

}
