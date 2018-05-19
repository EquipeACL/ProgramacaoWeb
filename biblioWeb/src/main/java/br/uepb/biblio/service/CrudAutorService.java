package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.jpaEntity.EntityAutor;

/**
 * Essa é a classe de Serviço do Autor, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudAutorService {
	private Logger logger = Logger.getLogger(CrudAutorService.class);
	@Autowired
	private Autores autores;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param autor, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityAutor salvar (Autor autor) {
		EntityAutor newEntity = new EntityAutor(autor);
		Optional <EntityAutor> autorOptional = autores.findByNomeIgnoreCase(newEntity.getNome());
		if(autorOptional.isPresent()){
			throw new ItemDuplicadoException("Autor já Cadastrado!");
		}
		try{
			return autores.saveAndFlush(newEntity);			
		}catch(Exception e){
			logger.error("Erro ao cadastrar autor!",e);
			return null;
		}
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Autor no banco de dados
	 * @return List<EntityAutor> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityAutor> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAutor a where a.nome like '%"+busca+"%'",EntityAutor.class).getResultList();
	}

	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param autor, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar(Autor autor){
		EntityAutor newEntity = new EntityAutor(autor);
		try {
			autores.save(newEntity);
			return true;			
		} catch (Exception e) {
			logger.error("Erro na atualização do autor!",e);
			return false;
		}
	}
	
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param autor, que é o objeto que irá ser removido da tabela de Autor no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover(EntityAutor autor) {
		if (autor != null && autor.getId()!=0) {
			autores.delete(autor);
			logger.info("Autor removido com sucesso.");
			return true;
		}
		return false;
		
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Autor no banco de dados.
	 */
	@Transactional
	public boolean remover(int id) {
		if (id != 0) {
			try {
				autores.delete(id);
				logger.info("Autor removido com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover autor",e);
			}
			
		}
		return false;
	}
}
