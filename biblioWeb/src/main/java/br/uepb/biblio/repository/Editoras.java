package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Editora;

@Repository
public interface Editoras extends JpaRepository<Editora,Integer> {
	
	public Optional<Editora> findByNomeIgnoreCase(String nome);

}
