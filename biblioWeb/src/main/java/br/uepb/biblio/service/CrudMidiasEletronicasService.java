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

/**
 * Essa é a classe de Serviço de MidiasEletronicas, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CrudMidiasEletronicasService {
	private static Logger logger = Logger.getLogger(CrudMidiasEletronicasService.class);
	@Autowired
	private Midias midias;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param midia, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityMidiasEletronicas salvar (MidiasEletronicas midia) {
		EntityMidiasEletronicas newEntity = new EntityMidiasEletronicas(midia);
		Optional <EntityMidiasEletronicas> midiaOptional = midias.findByTituloIgnoreCase(newEntity.getTitulo());
		if(midiaOptional.isPresent()){
			ItemDuplicadoException e = new ItemDuplicadoException(" Midia já Cadastrada!");
			logger.error("Erro ao cadastrar midia",e);
			throw e;
		}
		try {
			logger.info("Midia cadastrada com sucesso!");
			return midias.saveAndFlush(newEntity);			
		} catch (Exception e) {
			logger.error("Erro ao cadastrar Midia!",e);
			return null;
		}
		
	}
	
	/**
	 * Esse é o método responsável por atualizar um objeto no banco de dados
	 * @param midia, que é o objeto que irá ser atualizado no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha na atualização
	 */
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
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por MidiaEletronica no banco de dados
	 * @return List<EntityJornal> contendo o(s) objeto(s) referente(s) à busca
	 */
	@Transactional
	public List<EntityMidiasEletronicas> buscarPorTitulo (String busca) {
		return manager.createQuery("select m from EntityMidiasEletronicas m where m.titulo like '%"+busca+"%'",EntityMidiasEletronicas.class).getResultList();
	}	
	
	/**
	 * Esse é o método responsável por remover um objeto no banco de dados
	 * @param id, que é o id do objeto que irá ser removido da tabela de MidiaEletronica no banco de dados.
	 * @return true or false, dependendo do sucesso ou falha da remoção
	 */
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
