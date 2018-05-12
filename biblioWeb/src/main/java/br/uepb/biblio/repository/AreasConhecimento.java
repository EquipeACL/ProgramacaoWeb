package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.EntityAreaConhecimento;

@Repository
public interface AreasConhecimento extends JpaRepository<EntityAreaConhecimento,Integer>{

	public Optional <EntityAreaConhecimento> findByNomeIgnoreCase(String nome);

}
