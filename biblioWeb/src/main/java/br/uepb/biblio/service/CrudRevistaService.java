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
	private static Logger logger = Logger.getLogger(CadastroCursoService.class);
	@Autowired
	private Revistas revistas;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Revista revista) {
		EntityRevista newEntity = new EntityRevista(revista);
		Optional <EntityRevista> revistaOptional = revistas.findByTituloIgnoreCase(newEntity.getTitulo());
		if(revistaOptional.isPresent()){
			throw new ItemDuplicadoException(" Revista já Cadastrada!");
		}
		try{
			revistas.save(newEntity);
		}catch(Exception e){
			logger.error("Erro ao cadastar!",e);
		}
	}
	
	@Transactional
	public List<EntityRevista> buscarPorTitulo (String busca) {
		return manager.createQuery("select r from EntityRevista r where r.titulo like '%"+busca+"%'",EntityRevista.class).getResultList();
	}
	
	@Transactional
	public void atualizar (Revista revista) {
		EntityRevista newEntity = new EntityRevista(revista);
		try{
			revistas.save(newEntity);
		}catch(Exception e){
			logger.error("Erro ao atualizar!",e);
		}
	}
	
	@Transactional
	public void remover(int id) {
		if(id > 0){
			revistas.delete(id);;
		}		
	}

}
