package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.enums.Tipo_midia;

/**
 * Classe utilizada como modelo para um objeto do tipo MidiasEletronicas.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe MidiasEletronicas extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */
public class MidiasEletronicas extends ItemAcervo implements IFAcervo{
	
	@NotNull(message=" Tipo não pode ser nulo!")
	private Tipo_midia tipo;
	
	/**
	 * Método construtor da classe MidiasEletronicas
	 * Construtor vazio (utilizado para criar um objeto do tipo MidiasEletronicas sem parametros definidos)
	 */
	public MidiasEletronicas(){	
	}
	
	/**
	 * Método construtor da classe MidiasEletronicas (utilizado para criar um objeto do tipo MidiasEletronicas com parametros definidos)
	 * @param titulo titulo da midia eletronica
	 * @param tipo objeto Enum que define o tipo da midia eletronica
	 * @param data_gravacao data da gravação da midia eletronica
	 */
	public MidiasEletronicas(String titulo, Tipo_midia tipo, Date data_gravacao) {
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
