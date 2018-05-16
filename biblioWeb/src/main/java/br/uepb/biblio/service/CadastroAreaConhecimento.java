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


@Service
public class CadastroAreaConhecimento {
	private Logger logger = Logger.getLogger(CadastroAreaConhecimento.class);
	@Autowired
	private AreasConhecimento areasConhecimento;
	
	@PersistenceContext
    private EntityManager manager;
	
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
	
	@Transactional
	public List<EntityAreaConhecimento> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAreaConhecimento a where a.nome like '%"+busca+"%'",EntityAreaConhecimento.class).getResultList();
	}

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
