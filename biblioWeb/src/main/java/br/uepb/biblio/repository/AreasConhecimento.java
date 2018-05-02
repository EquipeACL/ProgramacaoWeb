package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.AreaConhecimento;

@Repository
public interface AreasConhecimento extends JpaRepository<AreaConhecimento,Integer>{

	public Optional <AreaConhecimento> findByNomeIgnoreCase(String nome);

}
