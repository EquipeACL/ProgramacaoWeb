package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Anais;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Anal;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.acervo.EntityAnal;

/**
 * Essa é a classe de Serviço de Anais, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudAnaisService {
	private static Logger logger = Logger.getLogger(CrudAnaisService.class);

	@Autowired
	private Anais anais;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param anal, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityAnal salvar (Anal anal) {
		EntityAnal newEntity = new EntityAnal(anal);
		Optional <EntityAnal> analOptional = anais.findByTituloIgnoreCase(newEntity.getTitulo());
		
		if(analOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Anal de Congresso já Cadastrada!");
			logger.error("Erro ao cadastrar anal",e);
			throw e;
		}
		try {
			return anais.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar anal",e);
		}
		return null;
	}
	
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Anal no banco de dados
	 * @return List<EntityAnal> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityAnal> buscarPorTitulo (String busca) {
		return manager.createQuery("select a from EntityAnal a where a.titulo like '%"+busca+"%'",EntityAnal.class).getResultList();
	}	
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param anal, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
	@Transactional
	public boolean atualizar(Anal anal) throws Exception {
		EntityAnal newEntity = new EntityAnal(anal);
		try {
			if (anal!=null && anal.getId()>0 ) {
				anais.save(newEntity);
				logger.info("Anal atualizado com sucesso.");
				return true;
			}
		} catch (Exception e) {
			logger.error("Erro ao atualizar o anal",e);
		}
		return false;
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Anal no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover(int id) {
		if (id != 0) {
			try {
				anais.delete(id);
				logger.info("Anal removido com sucesso.");
				return true;

			} catch (Exception e) {
				logger.error("Erro ao remover anal", e);
			}
		}
		return false;

	}

}
