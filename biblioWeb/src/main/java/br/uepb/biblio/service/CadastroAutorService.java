package br.uepb.biblio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.exception.NomeAutorJaCadastradoException;
import br.uepb.model.Autor;

@Service
public class CadastroAutorService {

	@Autowired
	private Autores autores;
	
	@Transactional
	public void salvar (Autor autor) {
		Optional <Autor> autorOptional = autores.findByNomeIgnoreCase(autor.getNome());
		if(autorOptional.isPresent()){
			throw new NomeAutorJaCadastradoException("Autor já Cadastrado");
		}
		autores.save(autor);
	}
	
}
