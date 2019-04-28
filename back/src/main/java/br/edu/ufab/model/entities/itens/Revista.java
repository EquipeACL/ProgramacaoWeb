package br.edu.ufab.model.entities.itens;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
/**
 * Classe que representa uma view dos dados de revista. Como estamos
 * usando hibernate, a classe Revista � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */
@Entity
public class Revista extends Impresso {

	@NotNull(message=" Data é obrigatório")
	private Date datapublicacao;

	public Date getDatapublicacao() {
		return datapublicacao;
	}

	public void setDatapublicacao(Date datapublicacao) {
		this.datapublicacao = datapublicacao;
	}
}
