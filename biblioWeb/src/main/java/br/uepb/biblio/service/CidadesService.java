package br.uepb.biblio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Cidades;
import br.uepb.model.jpaEntity.EntityCidade;
@Service
public class CidadesService {
	
	@Autowired
	private Cidades cidades;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public List<EntityCidade> buscarPorUf(String uf){
		return manager.createQuery("select e from EntityCidade e where e.uf = '"+uf+"'",EntityCidade.class).getResultList();
	}

}
