package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Midias_Eletronicas;

@Repository
public interface Midias extends JpaRepository<Midias_Eletronicas,Integer> {
	
	public Optional <Midias_Eletronicas> findByTituloIgnoreCase(String titulo);

}
