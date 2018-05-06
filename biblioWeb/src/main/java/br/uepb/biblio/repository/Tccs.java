package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Tcc;

@Repository
public interface Tccs extends JpaRepository<Tcc, Integer>{
	public Optional<Tcc> findByTituloIgnoreCase(String nome);
}
