package br.uepb.biblio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Autores;
import br.uepb.model.Autor;

@Service
public class CadastroAutorService {

	@Autowired
	Autores autores;
	
	@Transactional
	public void salvar (Autor autor) {
		autores.save(autor);
	}
	
}
