package br.uepb.model.acervo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.enums.Tipo_midia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Midias_Eletronicas.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Midias_Eletronicas implementa a interface Acervo
 * @author EquipeACL
 */
@Entity
@Table(name="midia")
public class Midias_Eletronicas extends ItemAcervo implements IFAcervo{
	
	@Transient
	@NotBlank(message="Data obrigatória")
	private String data_string;
	public String getData_string() {
		return data_string;
	}

	public void setData_string(String data_string) {
		this.data_string = data_string;
	}

	
	@NotNull(message=" Tipo não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	private Tipo_midia tipo;
	
	/**
	 * M�todo construtor da classe Midias_Eletronicas
	 * Construtor vazio (utilizado para criar um objeto do tipo Midias_Eletronicas sem par�metros definidos)
	 */
	public Midias_Eletronicas(){	
	}
	
	/**
	 * M�todo construtor da classe Midias_Eletronicas (utilizado para criar um objeto do tipo Midias_Eletronicas com par�metros definidos)
	 * @param titulo, t�tulo da m�dia eletr�nica
	 * @param tipo, objeto Enum que define o tipo da m�da eletr�nica
	 * @param data_gravacao, data da grava��o da m�dia eletr�nica
	 */
	public Midias_Eletronicas(String titulo, Tipo_midia tipo, Date data_gravacao) {
		setTitulo(titulo);
		setTipo(tipo);
		setData_gravacao(data_gravacao);
	}
	
	public Tipo_midia getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_midia tipo) {
		this.tipo = tipo;
	}

	public Date getData_gravacao() {
		return getData();
	}

	public void setData_gravacao(Date data_gravacao) {
		setData(data_gravacao);
	}

	public boolean validaItem() {
		return true;
	}	

}
