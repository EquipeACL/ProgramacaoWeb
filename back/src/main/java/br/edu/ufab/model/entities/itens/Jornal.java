package br.edu.ufab.model.entities.itens;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa uma view dos dados de jornal. Como estamos
 * usando hibernate, a classe Jornal � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */

@Entity
public class Jornal extends Impresso {

	@NotNull(message=" Data é obrigatório")
	private Date data;
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
