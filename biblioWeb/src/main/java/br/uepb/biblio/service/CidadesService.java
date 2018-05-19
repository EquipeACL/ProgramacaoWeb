package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Cidade;
import br.uepb.model.jpaEntity.EntityCidade;

/**
 * Essa é a classe de Serviço de Cidades, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CidadesService {
	private static Logger logger = Logger.getLogger(CidadesService.class);
	@Autowired
	private Cidades cidades;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por realizar buscas por UF no banco de dados
	 * @param uf, que é a String contendo a UF 
	 * @return List<EntityCidade> contendo o(s) objeto(s) referente(s) a busca
	 */
	@Transactional
	public List<EntityCidade> buscarPorUf(String uf){
		return manager.createQuery("select e from EntityCidade e where e.uf = '"+uf+"'",EntityCidade.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Cidade no banco de dados
	 * @return List<EntityCidade> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public EntityCidade buscarPorNome(String nome){
		return manager.createQuery("select e from EntityCidade e where e.nome = '"+nome+"'",EntityCidade.class).getSingleResult();
	}
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param cidade, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityCidade salvar (Cidade cidade) {
		EntityCidade newEntity = new EntityCidade(cidade);
		Optional <EntityCidade> cidadeOptional = cidades.findByNomeIgnoreCase(newEntity.getNome());
		if(cidadeOptional.isPresent()){
			throw new ItemDuplicadoException(" Cidade já Cadastrada!");
		}
		try {
			return cidades.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar cidade!",e);			
		}
		return null;
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Cidade no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover(int id) {
		if(id>0){
			try {
				cidades.delete(id);
				logger.info("Cidade removida com sucesso.");
				return true;
			} catch (Exception e) {
				logger.info(" Erro ao remover a Cidade.");				
			}
		}
		return false;
	}

}
