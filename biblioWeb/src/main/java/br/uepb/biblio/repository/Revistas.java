package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Revista;

@Repository
public interface Revistas extends JpaRepository<Revista, Integer>{
	public Optional<Revista> findByTituloIgnoreCase(String nome);
}
