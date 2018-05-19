package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityTema;

/**
 * Esse é o repositório da classe Temas, responsável por conter  métodos que tratam a Entidade Tema
 * @author EquipeACL
 *
 */
@Repository
public interface Temas extends JpaRepository<EntityTema, Integer> {
	
	public Optional<EntityTema> findByNomeIgnoreCase(String nome);

}
