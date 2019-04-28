package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

import br.edu.ufab.model.entities.Autor;

/**
 * Classe abstrata que gera os campos de local e autores.
 * Esta classe herda de ItemAcervo, pois Trabalho academico é um tipo de Item do Acervo.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
 * */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TrabalhoAcademico extends ItemAcervo {

	@NotBlank(message=" Local é obrigatório")
	private String local;
	

	@ManyToMany(fetch =FetchType.EAGER)
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
