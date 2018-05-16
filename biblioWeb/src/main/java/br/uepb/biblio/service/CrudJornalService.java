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

@Service
public class CrudJornalService  {
	private static Logger logger = Logger.getLogger(CrudJornalService.class);
	@Autowired
	private Jornais jornais;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Jornal jornal) {
		EntityJornal newEntity = new EntityJornal(jornal);
		Optional <EntityJornal> jornalOptional = jornais.findByTituloIgnoreCase(newEntity.getTitulo());
		if(jornalOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Jornal já Cadastrado!");
			logger.error("Erro ao cadastrar Jornal!",e);
			throw e;
		}
		try{
			jornais.save(newEntity);
			logger.info("Jornal salvo com sucesso!");
		}catch(Exception e){
			logger.error("Erro ao cadastrar Jornal!",e);
		}
	}
	
	@Transactional
	public List<EntityJornal> buscarPorTitulo (String busca) {
		return manager.createQuery("select j from EntityJornal j where j.titulo like '%"+busca+"%'",EntityJornal.class).getResultList();
	}
	
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
