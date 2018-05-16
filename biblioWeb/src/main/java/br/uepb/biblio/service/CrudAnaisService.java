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
import br.uepb.model.jpaEntity.acervo.EntityAnal;

@Service
public class CrudAnaisService {
	private static Logger logger = Logger.getLogger(CrudAnaisService.class);

	@Autowired
	private Anais anais;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Anal anal) {
		EntityAnal newEntity = new EntityAnal(anal);
		Optional <EntityAnal> analOptional = anais.findByTituloIgnoreCase(newEntity.getTitulo());
		
		if(analOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Anal de Congresso já Cadastrada!");
			logger.error("Erro ao cadastrar anal",e);
			throw e;
		}
		anais.save(newEntity);
	}
	
	@Transactional
	public List<EntityAnal> buscarPorTitulo (String busca) {
		return manager.createQuery("select a from EntityAnal a where a.titulo like '%"+busca+"%'",EntityAnal.class).getResultList();
	}	
	
	@Transactional
	public void atualizar(Anal anal) throws Exception {
		EntityAnal newEntity = new EntityAnal(anal);
		try {
			if (anal!=null || anal.getId()>0 ) {
				anais.save(newEntity);
				logger.info("Anal atualizado com sucesso.");
			}
		} catch (Exception e) {
			logger.error("Erro ao atualizar o anal",e);
		}
	}

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
