package br.uepb.biblio.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.model.Grupo;
import br.uepb.model.jpaEntity.EntityOrientador;

/**
 * Essa é a classe de Serviço do Grupo, que contém os métodos responsáveis por realizar buscas desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudGrupoService {
	
	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * ESse método é responsável por realizar uma busca de grupos que não contenham alunos na tabela Grupo do banco de dados
	 * @return List<Grupo> contendo os objetos referentes à busca
	 */
	@Transactional
	public List<Grupo> buscaGrupos() {
		return manager.createQuery("select a from Grupo a where a.nome !='aluno'",Grupo.class).getResultList();
	}
	
	/**
	 * ESse método é responsável por realizar uma busca de alunos na tabela Grupo do banco de dados
	 * @return List<Grupo> contendo os objetos referentes à busca
	 */
	@Transactional
	public List<Grupo> buscaAluno() {
		return manager.createQuery("select a from Grupo a where a.nome ='aluno'",Grupo.class).getResultList();
	}
}
