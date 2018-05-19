package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Tema;
import br.uepb.model.jpaEntity.EntityTema;

/**
 * Essa é a classe de Serviço do Tema, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CadastroTemaService {
	private static Logger logger = Logger.getLogger(CadastroTemaService.class);
	@Autowired
	private Temas temas;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param tema, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityTema salvar (Tema tema) {
		EntityTema newEntity = new EntityTema(tema);
		Optional <EntityTema> temaOptional = temas.findByNomeIgnoreCase(newEntity.getNome());
		if(temaOptional.isPresent()){
			throw new ItemDuplicadoException(" Tema já Cadastrado!");
		}
		try {
			return temas.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Tema!",e);
			return null;
		}
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Orientador no banco de dados
	 * @return List<EntityOrientador> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityTema> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityTema a where a.nome like '%"+busca+"%'",EntityTema.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Tema no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover (int  id) {
		if(id > 0){
			try {
				temas.delete(id);
				logger.info("Tema removido com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover Tema!",e);
				
			}
		}
		return false;
		
		
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param tema, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */ 
	@Transactional
	public boolean atualizar(Tema tema) throws Exception {
		EntityTema newEntity = new EntityTema(tema);
		try{
			if(newEntity.getId()!=0){
				temas.save(newEntity);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização",e);
		}
		logger.info("Tema atualizado com sucesso.");
		return true;
	}
	
	/**
	 * Esse método realiza uma busca de um objeto do tipo Tema no banco de dados pela área de conhecimento
	 * @param id, que é o id da área de conhecimento
	 * @return List<EntityTema> contendo o(s) parâmetro(s) resultante(s) da busca
	 */
	@Transactional
	public List<EntityTema> buscarPorArea (int id) {
		return manager.createQuery("select a from EntityTema a where a.area = '"+id+"'",EntityTema.class).getResultList();
	}
}
