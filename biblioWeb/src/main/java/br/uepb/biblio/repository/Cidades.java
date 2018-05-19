package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityCidade;

/**
 * Esse é o repositório da classe Cidades, responsável por conter  métodos que tratam a Entidade Cidade
 * @author EquipeACL
 *
 */
@Repository
public interface Cidades extends JpaRepository <EntityCidade,Integer>{
	
	public Optional <EntityCidade> findByNomeIgnoreCase(String nome);
	

}
