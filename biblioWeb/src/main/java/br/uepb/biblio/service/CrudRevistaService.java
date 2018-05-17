package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Revista;
import br.uepb.model.jpaEntity.acervo.EntityRevista;
@Service
public class CrudRevistaService {
	private static Logger logger = Logger.getLogger(CrudRevistaService.class);
	@Autowired
	private Revistas revistas;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public EntityRevista salvar (Revista revista) {
		EntityRevista newEntity = new EntityRevista(revista);
		Optional <EntityRevista> revistaOptional = revistas.findByTituloIgnoreCase(newEntity.getTitulo());
		if(revistaOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Revista já Cadastrada!");
			logger.error("Erro ao cadastrar revista",e);
			throw e;
		}
		try{
			EntityRevista retorno = revistas.saveAndFlush(newEntity);
			logger.info("Revista cadastrada com sucesso");
			return retorno;
		}catch(Exception e){
			logger.error("Erro ao cadastar Revista!",e);
			return null;
		}
	}
	
	@Transactional
	public List<EntityRevista> buscarPorTitulo (String busca) {
		return manager.createQuery("select r from EntityRevista r where r.titulo like '%"+busca+"%'",EntityRevista.class).getResultList();
	}
	
	@Transactional
	public boolean atualizar (Revista revista) {
		EntityRevista newEntity = new EntityRevista(revista);
		try{
			revistas.save(newEntity);
			logger.info("Revista atualizada com sucesso");
			return true;
		}catch(Exception e){
			logger.error("Erro ao atualizar Revista!",e);
			return false;
		}
		
	}
	
	@Transactional
	public boolean remover(int id) {
		if(id > 0){
			try {
				revistas.delete(id);
				logger.info("Revista deletada com sucesso");
				return true;
			}
			catch(Exception e){
				logger.error("Erro ao remover tcc",e);
			}
			
		}		
		return false;
	}

}
