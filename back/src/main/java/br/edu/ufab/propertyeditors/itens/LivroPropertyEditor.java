package br.edu.ufab.propertyeditors.itens;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.itens.Livro;
import br.edu.ufab.model.repositories.itens.LivroRepository;

/**
 * Classe respons�vel por receber os dados completos de Livro, procurar sue id e retornar seu titulo, editora, autores,etc,
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */

@Component
public class LivroPropertyEditor extends PropertyEditorSupport {

	@Autowired private LivroRepository livroRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idLivro = Long.parseLong(text);
		Optional<Livro> livro = livroRepository.findById(idLivro);
		setValue(livro);
	}
}
