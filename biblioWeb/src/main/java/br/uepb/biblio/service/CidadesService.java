package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Cidade;
import br.uepb.model.jpaEntity.EntityCidade;
@Service
public class CidadesService {
	private static Logger logger = Logger.getLogger(CidadesService.class);
	@Autowired
	private Cidades cidades;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public List<EntityCidade> buscarPorUf(String uf){
		return manager.createQuery("select e from EntityCidade e where e.uf = '"+uf+"'",EntityCidade.class).getResultList();
	}
	
	@Transactional
	public EntityCidade buscarPorNome(String nome){
		return manager.createQuery("select e from EntityCidade e where e.nome = '"+nome+"'",EntityCidade.class).getSingleResult();
	}
	
	@Transactional
	public EntityCidade salvar (Cidade cidade) {
		EntityCidade newEntity = new EntityCidade(cidade);
		Optional <EntityCidade> cidadeOptional = cidades.findByNomeIgnoreCase(newEntity.getNome());
		if(cidadeOptional.isPresent()){
			throw new ItemDuplicadoException(" Cidade já Cadastrada!");
		}
		try {
			return cidades.saveAndFlush(newEntity);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar cidade!",e);			
		}
		return null;
	}
	
	@Transactional
	public boolean remover(int id) {
		if(id>0){
			try {
				cidades.delete(id);
				logger.info("Cidade removida com sucesso.");
				return true;
			} catch (Exception e) {
				logger.info(" Erro ao remover a Cidade.");				
			}
		}
		return false;
	}

}
