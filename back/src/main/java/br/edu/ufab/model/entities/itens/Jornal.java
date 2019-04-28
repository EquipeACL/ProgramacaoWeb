package br.edu.ufab.model.entities.itens;

import java.sql.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa uma entidade do tipo Jornal.
 * Como estamos usando hibernate, a classe Jornal será uma entidade no banco de dados e  seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
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
