package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.edu.ufab.model.entities.Autor;

/**
 * Classe sse abstrata que gera os campos de local e autores.
 * Esta classe herda de ItemAcervo, pois Trabalho academico � um tipo de Item do Acervo.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TrabalhoAcademico extends ItemAcervo {

	@NotBlank(message=" Local é obrigatório")
	private String local;
	
	@ManyToMany
	private Set<Autor> autores;

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Set<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Set<Autor> autores) {
		this.autores = autores;
	}
}
