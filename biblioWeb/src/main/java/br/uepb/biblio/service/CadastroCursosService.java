package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Curso;
import br.uepb.model.jpaEntity.EntityCurso;
@Service
public class CadastroCursosService {
	private static Logger logger = Logger.getLogger(CadastroCursoService.class);
	@Autowired
	private Cursos cursos;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Curso curso) {
		EntityCurso newEntity = new EntityCurso(curso);
		Optional <EntityCurso> cursoOptional = cursos.findByNomeIgnoreCase(newEntity.getNome());
		if(cursoOptional.isPresent()){
			throw new ItemDuplicadoException(" Curso já esta Cadastrado!");
		}
		try{
			cursos.save(newEntity);
		}catch(Exception e){
			logger.error("Erro ao cadastrar!",e);
		}
		
	}
	
	@Transactional
	public void atualizar (Curso curso) {
		EntityCurso newEntity = new EntityCurso(curso);
		try{
			cursos.save(newEntity);
		}catch(Exception e){
			logger.error("Erro ao atualizar!",e);
		}
		
	}
	
	@Transactional
	public List<EntityCurso> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityCurso a where a.nome like '%"+busca+"%'",EntityCurso.class).getResultList();
	}
	
	@Transactional
	public void remover(int id) {
		if (id != 0) {
			try{
				cursos.delete(id);
			}catch(Exception e){
				logger.error("Erro ao deletar!",e);
			}
		}
		logger.error("Curso deletado com sucesso!");
	}
}
