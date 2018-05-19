package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;
import br.uepb.model.jpaEntity.EntityEditora;

/**
 * Essa é a classe de Serviço do Editora, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudEditoraService {
	private Logger logger = Logger.getLogger(CrudEditoraService.class);
	@Autowired
	private Editoras editoras;
	
	@PersistenceContext
    private EntityManager manager;
	
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param editora, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityEditora salvar(Editora editora) {
		EntityEditora newEntity = new EntityEditora(editora);
		Optional <EntityEditora> optionalEditora = editoras.findByNomeIgnoreCase(newEntity.getNome());
		if(optionalEditora.isPresent()) {
			throw new ItemDuplicadoException("Editora já Cadastrada");
		}
		try {
			return editoras.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar editora",e);
		}
		return null;
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Editora no banco de dados
	 * @return List<EntityEditora> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityEditora> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityEditora a where a.nome like '%"+busca+"%'",EntityEditora.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param editora, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */ 
	@Transactional
	public void atualizar(Editora editora) throws Exception {
		EntityEditora newEntity = new EntityEditora(editora);
		try{
			if(editora.getId()>0){
				editoras.save(newEntity);
				logger.info("Editora atualizada com sucesso.");
			}
		}catch(Exception e){
			logger.error("Erro ao atualizar", e);
		}
	}
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Editora no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
	@Transactional
	public boolean remover (int  id) {
		if(id > 0){
			try {
				editoras.delete(id);
				logger.info("Editora removida com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover a editora",e);
			}
			
		}
		return false;

	
	}
}
