package br.edu.ufab.propertyeditors.pessoas;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.pessoas.Aluno;
import br.edu.ufab.model.repositories.pessoas.AlunoRepository;
/**
 * Classe respons�vel por receber os dados completos de Autor, procurar sue id e retornar seu nome,cpf,rg,etc.,
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */
@Component
public class AlunoPropertyEditor extends PropertyEditorSupport {

	@Autowired private AlunoRepository alunoRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idAluno = Long.parseLong(text);
		Optional<Aluno> aluno = alunoRepository.findById(idAluno);
		setValue(aluno);
	}
}
