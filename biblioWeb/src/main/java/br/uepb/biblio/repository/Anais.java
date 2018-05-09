package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Anal;

@Repository
public interface Anais extends JpaRepository<Anal, Integer>{
	
	public Optional<Anal> findByTituloIgnoreCase(String nome);

}
