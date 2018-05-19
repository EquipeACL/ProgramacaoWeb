package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Tccs;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.ItemNaoEncontradoException;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.jpaEntity.acervo.EntityTcc;

/**
 * Essa é a classe de Serviço de Tcc, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudTccService {
	private static Logger logger = Logger.getLogger(CrudTccService.class);
	@Autowired
	private Tccs tccs;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param tcc, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityTcc salvar (Tcc tcc) {
		EntityTcc newEntity = new EntityTcc(tcc);
		Optional<EntityTcc> tccOptional = tccs.findByTituloIgnoreCase(newEntity.getTitulo());
		if(tccOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" TCC já Cadastrada!");
			logger.error("Erro ao cadastrar TCC",e);
			throw e;
		}
		try {
			return tccs.save(newEntity);
			
		} catch (Exception e) {
			logger.error("Erro ao cadastrar TCC!",e);
			return null;
		}

	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Tcc no banco de dados
	 * @return List<EntityTcc> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityTcc> buscarPorTitulo (String busca) {
		return manager.createQuery("select t from EntityTcc t where t.titulo like '%"+busca+"%'",EntityTcc.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome do autor no banco de dados
	 * @param busca, que é a String que contém o nome do autor do Tcc no banco de dados
	 * @return List<EntityTcc> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityTcc> buscarPorAutor(String busca) {
		return manager.createQuery("select an from EntityTcc an inner join an.autor la where la.id = ALL(select a.id from EntityAutor a where a.nome like '%"+busca+"%')",EntityTcc.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param tcc, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar(Tcc tcc) {
		EntityTcc newEntity = new EntityTcc(tcc);
		try {
			tccs.save(newEntity);
			logger.info("Tcc atualizado com sucesso");
			return true;
		} catch (Exception e) {
			logger.error("Erro ao atualizar!",e);
			return false;
		}
		 
		
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Tcc no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover(int id) throws ItemNaoEncontradoException {
		
		if(id>0){
			try {
				tccs.delete(id);
				logger.info("Tcc deletado com sucesso");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover TCC",e);
				
			}
			
		}
		return false;
		
	}

}
