package br.uepb.biblio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.exception.NomeOrientadorJaCadastradoException;
import br.uepb.model.Orientador;

@Service
public class CadastroOrientadorService {

	@Autowired
	private Orientadores orientadores;
	
	@Transactional
	public void salvar(Orientador orientador) {
		Optional <Orientador> optionalOrientador = orientadores.findByNomeIgnoreCase(orientador.getNome());
		if(optionalOrientador.isPresent()) {
			throw new NomeOrientadorJaCadastradoException("Orientador já Cadastrado!");
		}
		orientadores.save(orientador);
	}
}
