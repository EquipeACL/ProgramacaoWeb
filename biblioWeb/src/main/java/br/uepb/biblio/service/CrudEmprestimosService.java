package br.uepb.biblio.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Emprestimos;
import br.uepb.model.Emprestimo;

@Service
public class CrudEmprestimosService {
	private static Logger logger = Logger.getLogger(CrudEmprestimosService.class);
	@Autowired
	private Emprestimos emprestimos;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public Emprestimo salvar(Emprestimo emprestimo){
		if(emprestimo.getAnal().getId()==0){
			emprestimo.setAnal(null);
		}
		if(emprestimo.getLivro().getId()==0){
			emprestimo.setLivro(null);
		}
		if(emprestimo.getJornal().getId()==0){
			emprestimo.setJornal(null);
		}
		if(emprestimo.getRevista().getId()==0){
			emprestimo.setRevista(null);
		}
		if(emprestimo.getMidia().getId()==0){
			emprestimo.setMidia(null);
		}
		if(emprestimo.getTcc().getId()==0){
			emprestimo.setTcc(null);
		}
		try {
			return emprestimos.saveAndFlush(emprestimo);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar emprestimo.",e);
			return null;
		}
	}

}
