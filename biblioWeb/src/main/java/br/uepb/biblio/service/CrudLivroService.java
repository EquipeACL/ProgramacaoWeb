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

/**
 * Essa é a classe de Serviço de Livro, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudLivroService {
	private static Logger logger = Logger.getLogger(CrudLivroService.class);
	@Autowired
	private Livros livros;
	
	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param livro, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityLivro salvar (Livro livro) {
		EntityLivro newEntity = new EntityLivro(livro);
		Optional <EntityLivro> livroOptional = livros.findByTituloIgnoreCase(newEntity.getTitulo());
		if(livroOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Livro já Cadastrado!");
			logger.error("Erro ao cadastrar o livro",e);
			throw e;
		}
		try {
			
			return livros.saveAndFlush(newEntity);
			
		} catch (Exception e) {
			logger.error("Erro ao cadastrar!",e);
			return null;
		}
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Livro no banco de dados
	 * @return List<EntityJornal> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityLivro> buscarPorTitulo (String busca) {
		return manager.createQuery("select l from EntityLivro l where l.titulo like '%"+busca+"%'",EntityLivro.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param livro, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar (Livro livro) {
		EntityLivro newEntity = new EntityLivro(livro);
		try {
			livros.save(newEntity);
			logger.info("Livro atualizado com sucesso!");
			return true;
		} catch (Exception e) {
			logger.error("Erro ao atualizar!",e);
			return false;
		}
	
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Livro no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover(int id){
		if(id>0){
			try {
				livros.delete(id);
				logger.info("Livro removido com sucesso");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover livro",e);
			}
			
		}
		
		return false;
	}

}
