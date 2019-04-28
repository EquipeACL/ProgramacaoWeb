package br.edu.ufab.propertyeditors.itens;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.itens.TCC;
import br.edu.ufab.model.repositories.itens.TCCRepository;
/**
 * Classe respons�vel por receber os dados completos de Autor, procurar seu id e retornar seu titulo,orientador,etc.,
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */
@Component
public class TCCPropertyEditor extends PropertyEditorSupport {

	@Autowired private TCCRepository tccRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idTCC = Long.parseLong(text);
		Optional<TCC> tcc = tccRepository.findById(idTCC);
		setValue(tcc);
	}
}
