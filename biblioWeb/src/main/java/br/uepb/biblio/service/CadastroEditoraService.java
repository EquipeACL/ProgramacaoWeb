package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;
import br.uepb.model.jpaEntity.EntityEditora;

@Service
public class CadastroEditoraService {
	private Logger logger = Logger.getLogger(CadastroEditoraService.class);
	@Autowired
	private Editoras editoras;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public EntityEditora salvar(Editora editora) {
		EntityEditora newEntity = new EntityEditora(editora);
		Optional <EntityEditora> optionalEditora = editoras.findByNomeIgnoreCase(newEntity.getNome());
		if(optionalEditora.isPresent()) {
			throw new ItemDuplicadoException("Editora já Cadastrada");
		}
		return editoras.saveAndFlush(newEntity);
	}
	
	@Transactional
	public List<EntityEditora> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityEditora a where a.nome like '%"+busca+"%'",EntityEditora.class).getResultList();
	}
	
	@Transactional
	public void atualizar(Editora editora) throws Exception {
		EntityEditora newEntity = new EntityEditora(editora);
		try{
			if(editora.getId()>0){
				editoras.save(newEntity);
				logger.info("Editora atualizada com sucesso.");
			}
		}catch(Exception e){
			logger.error("Erro ao atualizar", e);
		}
	}
	
	@Transactional
	public boolean remover (int  id) {
		if(id > 0){
			try {
				editoras.delete(id);
				logger.info("Editora removida com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover a editora",e);
			}
			
		}
		return false;

	
	}
}
