package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Jornal;

@Repository 
public interface Jornais extends JpaRepository<Jornal,Integer> {
	public Optional <Jornal> findByTituloIgnoreCase(String titulo);

}
