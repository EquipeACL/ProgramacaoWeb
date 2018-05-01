package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo ItemAcervo.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe ItemAcervo � a super classe dos itens de acervo (jornal, livro, midias_eletr�nicas)
 * @author EquipeACL
 */
public class ItemAcervo {
	
	private int id;
	@NotBlank(message="Data nao pode ser vazia")
	private  Date data;
	
	@NotNull(message="Edicao é obrigatória")
	private int edicao;
	
	@NotNull(message="Título é obrigatório")
	private String titulo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
