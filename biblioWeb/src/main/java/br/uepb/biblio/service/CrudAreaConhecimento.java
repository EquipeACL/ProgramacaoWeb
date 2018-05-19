package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.jpaEntity.EntityAreaConhecimento;

/**
 * Essa é a classe de Serviço do AreaConhecimento, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudAreaConhecimento {
	private Logger logger = Logger.getLogger(CrudAreaConhecimento.class);
	@Autowired
	private AreasConhecimento areasConhecimento;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param area, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityAreaConhecimento salvar (AreaConhecimento area) {
		EntityAreaConhecimento newEntity = new EntityAreaConhecimento(area);
		Optional <EntityAreaConhecimento> areaOptional = areasConhecimento.findByNomeIgnoreCase(newEntity.getNome());
		if(areaOptional.isPresent()){
			ItemDuplicadoException e =  new ItemDuplicadoException(" Area já Cadastrada!");
			logger.error("Erro ao cadastrar Area",e);
		}
		try {
			return areasConhecimento.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Area",e);
			throw e;
		}
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param area, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar(AreaConhecimento area) throws Exception {
		EntityAreaConhecimento newEntity = new EntityAreaConhecimento(area);
		try{
			if(area.getId()!=0){
				areasConhecimento.save(newEntity);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização");
			
		}
		return true;
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por AreaConhecimento no banco de dados
	 * @return List<Aluno> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityAreaConhecimento> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAreaConhecimento a where a.nome like '%"+busca+"%'",EntityAreaConhecimento.class).getResultList();
	}

	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de AreaConhecimento no banco de dados.
	 */
	@Transactional
	public boolean remover (int  id) {
		if(id != 0){
			
			
			try {
				areasConhecimento.delete(id);
				logger.info("");
				return true;
			} catch (Exception e) {
				
				
			}
			
		}
		return false;
	}
}
