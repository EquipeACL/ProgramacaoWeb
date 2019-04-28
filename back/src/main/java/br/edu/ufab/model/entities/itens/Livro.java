package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Autor;

/**
 * Classe que representa uma view dos dados de livro. Como estamos
 * usando hibernate, a classe Livro � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */

@Entity
public class Livro extends Impresso {

	@NotNull(message=" ISBN do livro é obrigatório")
	private int isbn;
	
	@ManyToMany
	private Set<Autor> autores;

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> atores) {
		this.autores = atores;
	}
}
