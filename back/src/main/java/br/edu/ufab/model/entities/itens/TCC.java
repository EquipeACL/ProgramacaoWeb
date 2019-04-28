package br.edu.ufab.model.entities.itens;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.enums.TipoDeTCC;

/**
 * Classe que representa uma view dos dados de tcc. Como estamos
 * usando hibernate, a classe Tcc � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */

@Entity
public class TCC extends TrabalhoAcademico {

	@NotNull(message=" Tipo do tcc é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeTCC tipo;
	
	@ManyToMany
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
