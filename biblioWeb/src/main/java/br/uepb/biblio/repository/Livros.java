package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.acervo.Livro;

@Repository
public interface Livros extends JpaRepository<Livro,Integer>{
	
	public Optional <Livro> findByTituloIgnoreCase(String nome);
}
