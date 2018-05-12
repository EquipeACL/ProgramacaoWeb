package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityLivro;

@Repository
public interface Livros extends JpaRepository<EntityLivro,Integer>{
	
	public Optional <EntityLivro> findByTituloIgnoreCase(String nome);
}
