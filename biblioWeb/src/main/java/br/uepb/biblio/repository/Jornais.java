package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityJornal;

@Repository 
public interface Jornais extends JpaRepository<EntityJornal,Integer> {
	public Optional <EntityJornal> findByTituloIgnoreCase(String titulo);

}
