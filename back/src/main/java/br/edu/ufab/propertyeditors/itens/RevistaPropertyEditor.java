package br.edu.ufab.propertyeditors.itens;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.itens.Revista;
import br.edu.ufab.model.repositories.itens.RevistaRepository;
/**
 * Classe respons�vel por receber os dados completos de revista, procurar seu id e retornar seu titulo,editora,etc.,
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */
@Component
public class RevistaPropertyEditor extends PropertyEditorSupport {

	@Autowired private RevistaRepository revistaRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idRevista = Long.parseLong(text);
		Optional<Revista> revista = revistaRepository.findById(idRevista);
		setValue(revista);
	}
}
