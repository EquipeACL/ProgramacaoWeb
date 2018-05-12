package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Livros;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Livro;
import br.uepb.model.jpaEntity.acervo.EntityLivro;
@Service
public class CrudLivroService {
	
	@Autowired
	private Livros livros;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public void salvar (Livro livro) {
		EntityLivro newEntity = new EntityLivro(livro);
		Optional <EntityLivro> livroOptional = livros.findByTituloIgnoreCase(newEntity.getTitulo());
		if(livroOptional.isPresent()){
			throw new ItemDuplicadoException(" Livro já Cadastrado!");
		}
		livros.save(newEntity);
	}
	
	@Transactional
	public List<Livro> buscarPorTitulo (String busca) {
		return manager.createQuery("select l from Livro l where l.titulo like '%"+busca+"%'",Livro.class).getResultList();
	}	

}
