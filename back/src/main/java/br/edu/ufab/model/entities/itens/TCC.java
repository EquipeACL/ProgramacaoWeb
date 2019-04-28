package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.enums.TipoDeTCC;

/**
 * Classe que representa uma entidade do tipo Tcc.
 * Como estamos usando hibernate, a classe Tcc  será uma entidade no banco de dados e seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
 * */

@Entity
public class TCC extends TrabalhoAcademico {

	@NotNull(message=" Tipo do tcc é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeTCC tipo;
	
	@ManyToMany(fetch =FetchType.EAGER)
	private Set<Orientador> orientadores;

	public TipoDeTCC getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeTCC tipo) {
		this.tipo = tipo;
	}

	public Set<Orientador> getOrientadores() {
		return orientadores;
	}

	public void setOrientadores(Set<Orientador> orientadores) {
		this.orientadores = orientadores;
	}
}
