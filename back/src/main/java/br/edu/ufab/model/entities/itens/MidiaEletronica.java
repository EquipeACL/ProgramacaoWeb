package br.edu.ufab.model.entities.itens;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.enums.TipoDeMidia;

/**
 * Classe que representa uma view dos dados de midia. Como estamos
 * usando hibernate, a classe MidiaEletronica � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */

@Entity
public class MidiaEletronica extends ItemAcervo {

	@NotNull(message=" Tipo de midia é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeMidia tipo;
	
	@NotNull(message=" Data é obrigatório")
	private Date data;

	public TipoDeMidia getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeMidia tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
