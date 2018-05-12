package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

@Repository
public interface Midias extends JpaRepository<EntityMidiasEletronicas,Integer> {
	
	public Optional <EntityMidiasEletronicas> findByTituloIgnoreCase(String titulo);

}
