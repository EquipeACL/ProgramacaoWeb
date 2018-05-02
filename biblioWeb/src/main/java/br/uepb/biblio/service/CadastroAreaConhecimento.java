package br.uepb.biblio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.service.exception.NomeAreaConhecimentoJaCadastradaException;
import br.uepb.model.AreaConhecimento;


@Service
public class CadastroAreaConhecimento {
	@Autowired
	private AreasConhecimento areasConhecimento;
	
	@Transactional
	public void salvar (AreaConhecimento area) {
		Optional <AreaConhecimento> areaOptional = areasConhecimento.findByNomeIgnoreCase(area.getNome());
		if(areaOptional.isPresent()){
			throw new NomeAreaConhecimentoJaCadastradaException(" Area já Cadastrada!");
		}
		areasConhecimento.save(area);
	}

}
