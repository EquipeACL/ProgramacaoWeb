package br.uepb.biblio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.model.Grupo;
import br.uepb.model.jpaEntity.EntityOrientador;

@Service
public class CadastroGrupoService {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public List<Grupo> buscaGrupos() {
		return manager.createQuery("select a from Grupo a where a.nome !='aluno'",Grupo.class).getResultList();
	}
}
