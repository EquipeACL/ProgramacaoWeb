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

@Service
public class CadastroTemaService {
	private static Logger logger = Logger.getLogger(CadastroTemaService.class);
	@Autowired
	private Temas temas;
	
	@PersistenceContext
    private EntityManager manager;
	
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
		
	@Transactional
	public List<EntityTema> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityTema a where a.nome like '%"+busca+"%'",EntityTema.class).getResultList();
	}
	
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
	
	@Transactional
	public List<EntityTema> buscarPorArea (int id) {
		return manager.createQuery("select a from EntityTema a where a.area = '"+id+"'",EntityTema.class).getResultList();
	}
}
