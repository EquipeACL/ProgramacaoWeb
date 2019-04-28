package br.edu.ufab.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.repositories.CursoRepository;

/**
 * Classe respons�vel por receber os dados completos de Curso, procurar sue id e retornar seu nome,tipo,c�digo e �rea
 * para mapear em outra tabela.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * 
 * */

@Component
public class CursoPropertyEditor extends PropertyEditorSupport {

	@Autowired private CursoRepository cursoRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		long idCurso = Long.parseLong(text);
		Optional<Curso> curso = cursoRepository.findById(idCurso);
		setValue(curso);
	}
}
