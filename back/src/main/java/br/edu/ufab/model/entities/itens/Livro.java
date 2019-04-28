package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Autor;

/**
 * Classe que representa uma entidade do tipo Livro.
 * Como estamos usando hibernate, a classe Livro será uma entidade no banco de dados e seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
 * */

@Entity
public class Livro extends Impresso {

	@NotNull(message=" ISBN do livro é obrigatório")
	private int isbn;
	
	@ManyToMany(fetch=FetchType.EAGER)
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
