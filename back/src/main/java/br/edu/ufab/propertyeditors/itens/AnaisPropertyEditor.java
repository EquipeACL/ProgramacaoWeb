package br.edu.ufab.propertyeditors.itens;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.itens.Anais;
import br.edu.ufab.model.repositories.itens.AnaisRepository;
/**
 * Classe respons�vel por receber os dados completos de anais, procurar seu id e retornar seu nome,tipo e autor
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */
@Component
public class AnaisPropertyEditor extends PropertyEditorSupport {

	@Autowired private AnaisRepository anaisRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idAnais = Long.parseLong(text);
		Optional<Anais> anais = anaisRepository.findById(idAnais);
		setValue(anais);
	}	
}
