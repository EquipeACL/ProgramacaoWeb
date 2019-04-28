package br.edu.ufab.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.repositories.OrientadorRepository;

/**
 * Classe respons�vel por receber os dados completos de orientador, procurar seu id e retornar seu nome
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */

@Component
public class OrientadorPropertyEditor extends PropertyEditorSupport {

	@Autowired private OrientadorRepository orientadorRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idOrientador = Long.parseLong(text);
		Optional<Orientador> orientador = orientadorRepository.findById(idOrientador);
		setValue(orientador);
	}	
}
