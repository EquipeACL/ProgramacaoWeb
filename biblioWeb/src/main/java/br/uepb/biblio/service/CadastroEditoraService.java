package br.uepb.biblio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;

@Service
public class CadastroEditoraService {

	@Autowired
	private Editoras editoras;
	
	@Transactional
	public void salvar(Editora editora) {
		Optional <Editora> optionalEditora = editoras.findByNomeIgnoreCase(editora.getNome());
		if(optionalEditora.isPresent()) {
			throw new ItemDuplicadoException("Editora já Cadastrada");
		}
		editoras.save(editora);
	}
}
