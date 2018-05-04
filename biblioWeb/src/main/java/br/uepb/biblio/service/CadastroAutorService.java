package br.uepb.biblio.service;

import java.util.Optional;

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
	
	@Transactional
	public Autor salvar (Autor autor) {
		Optional <Autor> autorOptional = autores.findByNomeIgnoreCase(autor.getNome());
		if(autorOptional.isPresent()){
			throw new ItemDuplicadoException("Autor já Cadastrado!");
		}
		return autores.saveAndFlush(autor);
	}

}
