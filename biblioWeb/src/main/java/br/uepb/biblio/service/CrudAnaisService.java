package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Anais;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Anal;

@Service
public class CrudAnaisService {
	
	@Autowired
	private Anais anais;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Anal anal) {
		Optional <Anal> analOptional = anais.findByTituloIgnoreCase(anal.getTitulo());
		if(analOptional.isPresent()){
			throw new ItemDuplicadoException(" Anal de Congresso já Cadastrada!");
		}
		anais.save(anal);
	}
	
	@Transactional
	public List<Anal> buscarPorTitulo (String busca) {
		return manager.createQuery("select a from Anal a where a.titulo like '%"+busca+"%'",Anal.class).getResultList();
	}	

}
