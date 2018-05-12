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
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

@Service
public class CrudMidiasEletronicasService {

	@Autowired
	private Midias midias;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (MidiasEletronicas midia) {
		EntityMidiasEletronicas newEntity = new EntityMidiasEletronicas(midia);
		Optional <EntityMidiasEletronicas> midiaOptional = midias.findByTituloIgnoreCase(newEntity.getTitulo());
		if(midiaOptional.isPresent()){
			throw new ItemDuplicadoException(" Midia já Cadastrada!");
		}
		midias.save(newEntity);
	}
	
	@Transactional
	public List<EntityMidiasEletronicas> buscarPorTitulo (String busca) {
		return manager.createQuery("select m from EntityMidiasEletronicas m where m.titulo like '%"+busca+"%'",EntityMidiasEletronicas.class).getResultList();
	}	

}
