package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Orientador;
import br.uepb.model.jpaEntity.EntityOrientador;

@Service
public class CadastroOrientadorService {
	private static Logger logger = Logger.getLogger(CadastroOrientadorService.class);
	@Autowired
	private Orientadores orientadores;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public EntityOrientador salvar(Orientador orientador) {
		EntityOrientador newEntity = new EntityOrientador(orientador);
		Optional <EntityOrientador> optionalOrientador = orientadores.findByNomeIgnoreCase(newEntity.getNome());
		if(optionalOrientador.isPresent()) {
			throw new ItemDuplicadoException("Orientador já Cadastrado!");
		}
		try {
			return orientadores.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Orientador!",e);
			return null;
		}
	}
	
	@Transactional
	public List<EntityOrientador> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityOrientador a where a.nome like '%"+busca+"%'",EntityOrientador.class).getResultList();
	}
	
	@Transactional
	public boolean atualizar(Orientador orientador) throws Exception {
		EntityOrientador newEntity = new EntityOrientador(orientador);
		try{
			if(orientador.getId()!=0){
				orientadores.save(newEntity);
			}
		}catch(Exception e){
			logger.error("Erro ao atualizar orientador!",e);
			return false;
		}
		return true;
	}
	@Transactional
	public boolean remover (int  id) {
		if(id != 0){
			try {
				orientadores.delete(id);
			} catch (Exception e) {
				logger.error("Erro ao remover Orientador!",e);
			}
			return true;
		}
		return false;
	}
	

}
