package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Revista;
@Service
public class CrudRevistaService {
	@Autowired
	private Revistas revistas;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Revista revista) {
		Optional <Revista> revistaOptional = revistas.findByTituloIgnoreCase(revista.getTitulo());
		if(revistaOptional.isPresent()){
			throw new ItemDuplicadoException(" Revista já Cadastrada!");
		}
		revistas.save(revista);
	}
	
	@Transactional
	public List<Revista> buscarPorTitulo (String busca) {
		return manager.createQuery("select r from Revista r where r.titulo like '%"+busca+"%'",Revista.class).getResultList();
	}	

}
