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

/**
 * Essa é a classe de Serviço do Curso, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CadastroCursosService {
	private static Logger logger = Logger.getLogger(CadastroCursosService.class);
	@Autowired
	private Cursos cursos;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param curso, que é o objeto que irá ser salvo no banco de dados.
	 */
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
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param curso, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
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
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Curso no banco de dados
	 * @return List<EntityCurso> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityCurso> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityCurso a where a.nome like '%"+busca+"%'",EntityCurso.class).getResultList();
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por tipo no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Curso no banco de dados
	 * @return List<EntityCurso> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityCurso> buscarPorTipo(String busca) {
		return manager.createQuery("select a from EntityCurso a where a.tipo like '%"+busca+"%'",EntityCurso.class).getResultList();
	}
	
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de Curso no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
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
