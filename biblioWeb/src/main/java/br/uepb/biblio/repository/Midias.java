package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

/**
 * Esse é o repositório da classe MidiasEletronicas, responsável por conter  métodos que tratam a Entidade MidiaEletronica
 * @author EquipeACL
 *
 */
@Repository
public interface Midias extends JpaRepository<EntityMidiasEletronicas,Integer> {
	
	public Optional <EntityMidiasEletronicas> findByTituloIgnoreCase(String titulo);

}
