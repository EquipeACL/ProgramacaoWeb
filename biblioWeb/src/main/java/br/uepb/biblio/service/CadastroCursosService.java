package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Curso;
@Service
public class CadastroCursosService {
	
	@Autowired
	private Cursos cursos;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Curso curso) {
		Optional <Curso> cursoOptional = cursos.findByNomeIgnoreCase(curso.getNome());
		if(cursoOptional.isPresent()){
			throw new ItemDuplicadoException(" Curso já esta Cadastrado!");
		}
		try{
			cursos.save(curso);
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	
	@Transactional
	public List<Curso> buscarPorNome (String busca) {
		return manager.createQuery("select a from Curso a where a.nome like '%"+busca+"%'",Curso.class).getResultList();
	}
}
