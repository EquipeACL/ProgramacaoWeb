package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Midias;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Jornal;
import br.uepb.model.acervo.Midias_Eletronicas;

@Service
public class CrudMidiasEletronicasService {

	@Autowired
	private Midias midias;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Midias_Eletronicas midia) {
		Optional <Midias_Eletronicas> midiaOptional = midias.findByTituloIgnoreCase(midia.getTitulo());
		if(midiaOptional.isPresent()){
			throw new ItemDuplicadoException(" Midia já Cadastrada!");
		}
		midias.save(midia);
	}
	
	@Transactional
	public List<Midias_Eletronicas> buscarPorTitulo (String busca) {
		return manager.createQuery("select m from Midias_Eletronicas m where m.titulo like '%"+busca+"%'",Midias_Eletronicas.class).getResultList();
	}	

}
