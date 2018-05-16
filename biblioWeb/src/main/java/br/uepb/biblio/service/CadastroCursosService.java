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
	private static Logger logger = Logger.getLogger(CadastroCursosService.class);
	@Autowired
	private Cursos cursos;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public EntityCurso salvar (Curso curso) {
		EntityCurso newEntity = new EntityCurso(curso);
		Optional <EntityCurso> cursoOptional = cursos.findByNomeIgnoreCase(newEntity.getNome());
		if(cursoOptional.isPresent()){
			throw new ItemDuplicadoException(" Curso já esta Cadastrado!");
		}
		try{
			return cursos.saveAndFlush(newEntity);
		}catch(Exception e){
			logger.error("Erro ao cadastrar!",e);
			return null;
		}
		
	}
	
	@Transactional
	public boolean atualizar (Curso curso) {
		EntityCurso newEntity = new EntityCurso(curso);
		try{
			cursos.save(newEntity);
			logger.info("Curso atualizado com sucesso.");
			return true;
		}catch(Exception e){
			logger.error("Erro ao atualizar!",e);
			return false;
		}
		
	}
	
	@Transactional
	public List<EntityCurso> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityCurso a where a.nome like '%"+busca+"%'",EntityCurso.class).getResultList();
	}
	
	@Transactional
	public boolean remover(int id) {
		if (id != 0) {
			try{
				cursos.delete(id);	
				logger.info("Curso deletado com sucesso!");
				return true;
			}catch(Exception e){
				logger.error("Erro ao deletar!",e);
				
			}
		}
		return false;
	}
}
