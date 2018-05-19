package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Orientador;
import br.uepb.model.jpaEntity.EntityOrientador;


/**
 * Essa é a classe de Serviço do Orientador, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CadastroOrientadorService {
	private static Logger logger = Logger.getLogger(CadastroOrientadorService.class);
	@Autowired
	private Orientadores orientadores;
	
	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param orientador, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityOrientador salvar(Orientador orientador) {
		EntityOrientador newEntity = new EntityOrientador(orientador);
		Optional <EntityOrientador> optionalOrientador = orientadores.findByNomeIgnoreCase(newEntity.getNome());
		if(optionalOrientador.isPresent()) {
			throw new ItemDuplicadoException("Orientador já Cadastrado!");
		}
		try {
			return orientadores.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Orientador!",e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param busca, que é a String que contém o parâmetro de busca por Orientador no banco de dados
	 * @return List<EntityOrientador> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityOrientador> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityOrientador a where a.nome like '%"+busca+"%'",EntityOrientador.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param orientador, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */ 
	@Transactional
	public boolean atualizar(Orientador orientador) throws Exception {
		EntityOrientador newEntity = new EntityOrientador(orientador);
		try{
			if(orientador.getId()!=0){
				orientadores.save(newEntity);
				logger.info("Orientador atualizado com sucesso.");
			}
		}catch(Exception e){
			logger.error("Erro ao atualizar orientador!",e);
			return false;
		}
		return true;
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Orientador no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover (int  id) {
		if(id > 0){
			try {
				orientadores.delete(id);
				logger.info("Orientador removido com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover Orientador!",e);
			}
			
		}
		return false;
	}
	

}
