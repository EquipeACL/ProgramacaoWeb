package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.uepb.model.Tema;

@Repository
public interface Temas extends JpaRepository<Tema, Integer> {
	
	public Optional<Tema> findByNomeIgnoreCase(String nome);

}
