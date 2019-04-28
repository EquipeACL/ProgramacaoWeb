package br.edu.ufab.model.entities.itens;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.enums.TipoDeAnais;
/**
 * Classe que representa uma view dos dados de anais. Como estamos
 * usando hibernate, a classe Anais � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */
@Entity
public class Anais extends TrabalhoAcademico {
	
	@NotNull(message = " Tipo de Anal é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeAnais tipo;

	@NotBlank(message = " Nome do congresso é obrigatório")
	private String nomecongreco;

	public TipoDeAnais getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeAnais tipo) {
		this.tipo = tipo;
	}

	public String getNomecongreco() {
		return nomecongreco;
	}

	public void setNomecongreco(String nomecongreco) {
		this.nomecongreco = nomecongreco;
	}
}
