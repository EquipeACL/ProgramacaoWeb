package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Livros;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Livro;
import br.uepb.model.jpaEntity.acervo.EntityLivro;
@Service
public class CrudLivroService {
	private static Logger logger = Logger.getLogger(CrudLivroService.class);
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
		try {
			livros.save(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar!",e);
		}
	}
	
	@Transactional
	public List<EntityLivro> buscarPorTitulo (String busca) {
		return manager.createQuery("select l from EntityLivro l where l.titulo like '%"+busca+"%'",EntityLivro.class).getResultList();
	}
	
	@Transactional
	public void atualizar (Livro livro) {
		EntityLivro newEntity = new EntityLivro(livro);
		try {
			livros.save(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao atualizar!",e);
		}
	}
	
	@Transactional
	public void remover(int id){
		if(id>0){
			livros.delete(id);
		}
	}

}
