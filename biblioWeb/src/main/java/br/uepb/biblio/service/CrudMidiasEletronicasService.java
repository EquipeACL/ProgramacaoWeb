package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Midias;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

@Service
public class CrudMidiasEletronicasService {
	private static Logger logger = Logger.getLogger(CrudMidiasEletronicasService.class);
	@Autowired
	private Midias midias;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (MidiasEletronicas midia) {
		EntityMidiasEletronicas newEntity = new EntityMidiasEletronicas(midia);
		Optional <EntityMidiasEletronicas> midiaOptional = midias.findByTituloIgnoreCase(newEntity.getTitulo());
		if(midiaOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Midia já Cadastrada!");
			logger.error("Erro ao cadastrar midia",e);
			throw e;
		}
		try {
			midias.save(newEntity);
			logger.info("Midia cadastrada com sucesso!");
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Midia!",e);
		}
	}
	
	@Transactional
	public boolean atualizar (MidiasEletronicas midia) {
		EntityMidiasEletronicas newEntity = new EntityMidiasEletronicas(midia);
		try{
			midias.save(newEntity);

		}catch(Exception e){
			logger.error("Erro ao atualizar Midia!",e);
			return false;
		}
		logger.info("Midia atualizada com sucesso");
		return true;
		
	}
	
	@Transactional
	public List<EntityMidiasEletronicas> buscarPorTitulo (String busca) {
		return manager.createQuery("select m from EntityMidiasEletronicas m where m.titulo like '%"+busca+"%'",EntityMidiasEletronicas.class).getResultList();
	}	
	
	@Transactional
	public boolean remover(int id) {
		if (id != 0) {
			try {
				midias.delete(id);
				logger.info("Midia deletada com sucesso!");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover midia",e);
			}
			
		}
		return true;
	}

}
