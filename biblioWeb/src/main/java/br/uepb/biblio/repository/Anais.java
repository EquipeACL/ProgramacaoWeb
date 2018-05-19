package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityAnal;

/**
 * Esse é o repositório da classe Anais, responsável por conter  métodos que tratam a Entidade Anal
 * @author EquipeACL
 *
 */
@Repository
public interface Anais extends JpaRepository<EntityAnal, Integer>{
	
	public Optional<EntityAnal> findByTituloIgnoreCase(String nome);

}
