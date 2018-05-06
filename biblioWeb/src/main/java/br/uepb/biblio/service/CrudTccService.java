package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Tccs;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Tcc;

@Service
public class CrudTccService {
	@Autowired
	private Tccs tccs;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Tcc tcc) {
		Optional <Tcc> tccOptional = tccs.findByTituloIgnoreCase(tcc.getTitulo());
		if(tccOptional.isPresent()){
			throw new ItemDuplicadoException(" Revista já Cadastrada!");
		}
		tccs.save(tcc);
	}
	
	@Transactional
	public List<Tcc> buscarPorTitulo (String busca) {
		return manager.createQuery("select t from Tcc t where t.titulo like '%"+busca+"%'",Tcc.class).getResultList();
	}	

}
