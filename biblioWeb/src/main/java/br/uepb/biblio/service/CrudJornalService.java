package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Jornais;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Jornal;

@Service
public class CrudJornalService  {
	@Autowired
	private Jornais jornais;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Jornal jornal) {
		Optional <Jornal> jornalOptional = jornais.findByTituloIgnoreCase(jornal.getTitulo());
		if(jornalOptional.isPresent()){
			throw new ItemDuplicadoException(" Jornal já Cadastrado!");
		}
		jornais.save(jornal);
	}
	
	@Transactional
	public List<Jornal> buscarPorTitulo (String busca) {
		return manager.createQuery("select j from Jornal j where j.titulo like '%"+busca+"%'",Jornal.class).getResultList();
	}	

}
