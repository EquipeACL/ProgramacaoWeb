package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Jornais;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Jornal;
import br.uepb.model.jpaEntity.acervo.EntityJornal;

/**
 * Essa é a classe de Serviço de Jornal, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudJornalService  {
	private static Logger logger = Logger.getLogger(CrudJornalService.class);
	@Autowired
	private Jornais jornais;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param jornal, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityJornal salvar (Jornal jornal) {
		EntityJornal newEntity = new EntityJornal(jornal);
		Optional <EntityJornal> jornalOptional = jornais.findByTituloIgnoreCase(newEntity.getTitulo());
		if(jornalOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Jornal já Cadastrado!");
			logger.error("Erro ao cadastrar Jornal!",e);
			throw e;
		}
		try{
			return jornais.saveAndFlush(newEntity);
		
		}catch(Exception e){
			logger.error("Erro ao cadastrar Jornal!",e);
		}
		return null;
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Jornal no banco de dados
	 * @return List<EntityJornal> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityJornal> buscarPorTitulo (String busca) {
		return manager.createQuery("select j from EntityJornal j where j.titulo like '%"+busca+"%'",EntityJornal.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param jornal, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar(Jornal jornal) {
		EntityJornal newEntity = new EntityJornal(jornal);
		try{
			jornais.save(newEntity);
		}catch(Exception e){
			logger.error("Erro ao atualizar jornal!",e);
			return false;
		}
		logger.info("Jornal atualizado com sucesso!");
		return true;
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Jornal no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover (int id) {
		if(id>0){
			try {
				jornais.delete(id);
				logger.info("Jornal removido com sucesso!");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover jornal",e);
			}
			
		}
		return false;
		


	}

}
