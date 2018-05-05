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

@Service
public class CadastroAutorService {

	@Autowired
	private Autores autores;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public Autor salvar (Autor autor) {
		Optional <Autor> autorOptional = autores.findByNomeIgnoreCase(autor.getNome());
		if(autorOptional.isPresent()){
			throw new ItemDuplicadoException("Autor já Cadastrado!");
		}
		return autores.saveAndFlush(autor);
	}
	
	@Transactional
	public List<Autor> buscarPorNome (String busca) {
		return manager.createQuery("select a from Autor a where a.nome like '%"+busca+"%'",Autor.class).getResultList();
	}

}
