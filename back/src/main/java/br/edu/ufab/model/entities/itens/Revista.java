package br.edu.ufab.model.entities.itens;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
/**
 * Classe que representa uma entidade do tipo Revista.
 * Como estamos usando hibernate, a classe Revista será uma entidade no banco de dados e seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
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
