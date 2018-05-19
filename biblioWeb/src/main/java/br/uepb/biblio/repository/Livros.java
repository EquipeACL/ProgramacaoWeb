package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.acervo.EntityLivro;


/**
 * Esse é o repositório da classe Livros, responsável por conter  métodos que tratam a Entidade Livro
 * @author EquipeACL
 *
 */
@Repository
public interface Livros extends JpaRepository<EntityLivro,Integer>{
	
	public Optional <EntityLivro> findByTituloIgnoreCase(String nome);
}
