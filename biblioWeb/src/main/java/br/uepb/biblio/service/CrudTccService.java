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

@Service
public class CrudTccService {
	private static Logger logger = Logger.getLogger(CrudTccService.class);
	@Autowired
	private Tccs tccs;
	
	@PersistenceContext
    private EntityManager manager;
	
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
	
	@Transactional
	public List<EntityTcc> buscarPorTitulo (String busca) {
		return manager.createQuery("select t from EntityTcc t where t.titulo like '%"+busca+"%'",EntityTcc.class).getResultList();
	}
	
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
