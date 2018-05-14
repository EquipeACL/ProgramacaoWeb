package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.jpaEntity.EntityAutor;

@Service
public class CadastroAutorService {
	private Logger logger = Logger.getLogger(CadastroAutorService.class);
	@Autowired
	private Autores autores;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public EntityAutor salvar (Autor autor) {
		EntityAutor newEntity = new EntityAutor(autor);
		Optional <EntityAutor> autorOptional = autores.findByNomeIgnoreCase(newEntity.getNome());
		if(autorOptional.isPresent()){
			throw new ItemDuplicadoException("Autor já Cadastrado!");
		}
		try{
			return autores.saveAndFlush(newEntity);			
		}catch(Exception e){
			logger.error("Erro ao cadastrar autor!",e);
			return null;
		}
	}
	
	@Transactional
	public List<EntityAutor> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAutor a where a.nome like '%"+busca+"%'",EntityAutor.class).getResultList();
	}


	@Transactional
	public boolean atualizar(Autor autor){
		EntityAutor newEntity = new EntityAutor(autor);
		try {
			autores.save(newEntity);
			return true;			
		} catch (Exception e) {
			logger.error("Erro na atualização do autor!",e);
			return false;
		}
	}
	
	@Transactional
	public boolean remover(EntityAutor autor) {
		if (autor != null && autor.getId()!=0) {
			autores.delete(autor);
			return true;
		}
		return false;
	}
	
	@Transactional
	public boolean remover(int id) {
		if (id != 0) {
			autores.delete(id);
			return true;
		}
		return false;
	}
}
