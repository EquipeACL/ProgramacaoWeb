package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.jpaEntity.EntityAutor;

@Service
public class CadastroAutorService {

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
		return autores.saveAndFlush(newEntity);
	}
	
	@Transactional
	public List<EntityAutor> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAutor a where a.nome like '%"+busca+"%'",EntityAutor.class).getResultList();
	}


	@Transactional
	public void atualizar(Autor autor) throws Exception {
		EntityAutor newEntity = new EntityAutor(autor);
		try {
			if (autor.getId() != 0) {
				autores.save(newEntity);
			}
		} catch (Exception e) {
			throw new Exception("Erro na atualização");
		}
	}

	@Transactional
	public void remover(int id) {
		if (id != 0) {
			autores.delete(id);
		}

	}
}
