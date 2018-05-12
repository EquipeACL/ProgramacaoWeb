package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityCurso;
@Repository
public interface Cursos extends JpaRepository<EntityCurso, Integer>{
	
	public Optional <EntityCurso> findByNomeIgnoreCase(String nome);

}
