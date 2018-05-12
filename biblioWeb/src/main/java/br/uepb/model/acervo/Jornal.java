package br.uepb.model.acervo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;

/**
 * Classe utilizada como modelo para um objeto do tipo Jornal.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe Jornal extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */
public class Jornal extends ItemAcervo implements IFAcervo{
	@NotBlank(message="Data obrigatória")
	private String data_string;
	/**
	 * Método construtor da classe Jornal
	 * Construtor vazio (utilizado para criar um objeto do tipo Jornal sem parametros definidos)
	 */
	public Jornal() {
		
	}
	
	/**
	 * Método construtor da classe Jornal (utilizado para criar um objeto do tipo Jornal com parametros definidos)
	 * @param titulo titulo do jornal
	 * @param data data do jornal
	 * @param edicao edicao do jornal
	 */
	public Jornal(String titulo, Date data, int edicao) {
		setTitulo(titulo);
		setData(data);
		setEdicao(edicao);
	}
	
	public String getData_string() {
		return data_string;
	}

	public void setData_string(String data_string) {
		this.data_string = data_string;
	}

	public boolean validaItem() {
		return true;
	}


}
