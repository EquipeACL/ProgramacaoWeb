package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Orientador;

@Repository
public interface Orientadores extends JpaRepository <Orientador,Integer>{
	
	Optional <Orientador> findByNomeIgnoreCase(String nome);

}
