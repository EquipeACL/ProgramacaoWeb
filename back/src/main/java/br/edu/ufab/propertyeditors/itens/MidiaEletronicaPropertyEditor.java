package br.edu.ufab.propertyeditors.itens;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.itens.MidiaEletronica;
import br.edu.ufab.model.repositories.itens.MidiaEletronicaRepository;

/**
 * Classe respons�vel por receber os dados completos de Midiaeletronica, procurar sue id e retornar seu titulo,tipo,etc
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */

@Component
public class MidiaEletronicaPropertyEditor extends PropertyEditorSupport {

	@Autowired private MidiaEletronicaRepository midiaEletronicaRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idMidia = Long.parseLong(text);
		Optional<MidiaEletronica> midia = midiaEletronicaRepository.findById(idMidia);
		setValue(midia);
	}	
}
