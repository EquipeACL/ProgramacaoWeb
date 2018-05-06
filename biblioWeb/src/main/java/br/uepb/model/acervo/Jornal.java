package br.uepb.model.acervo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Jornal.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Jornal estende a classe ItemAcervo
 * @author EquipeACL
 */
@Entity
@Table(name="jornal")
public class Jornal extends ItemAcervo implements IFAcervo{
	
	@Transient
	@NotBlank(message="Data obrigatória")
	private String data_string;
	public String getData_string() {
		return data_string;
	}

	public void setData_string(String data_string) {
		this.data_string = data_string;
	}

	/**
	 * M�todo construtor da classe Jornal
	 * Construtor vazio (utilizado para criar um objeto do tipo Jornal sem par�metros definidos)
	 */
	public Jornal() {
		
	}
	
	/**
	 * M�todo construtor da classe Jornal (utilizado para criar um objeto do tipo Jornal com par�metros definidos)
	 * @param titulo, titulo do jornal
	 * @param data, data do jornal
	 * @param edicao, edicao do jornal
	 */
	public Jornal(String titulo, Date data, int edicao) {
		setTitulo(titulo);
		setData(data);
		setEdicao(edicao);
	}

	public boolean validaItem() {
		return true;
	}


}
