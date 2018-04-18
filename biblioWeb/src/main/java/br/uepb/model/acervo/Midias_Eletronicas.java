package br.uepb.model.acervo;

import java.util.Date;

import br.uepb.model.enums.Tipo_midia;

/**
 * Essa classe � respons�vel por criar um objeto do tipo Midias_Eletronicas.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Midias_Eletronicas implementa a interface Acervo
 * @author EquipeACL
 */
public class Midias_Eletronicas implements Acervo{
	private int id;
	private String titulo;
	private Tipo_midia tipo;
	private Date data_gravacao;
	
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Tipo_midia getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_midia tipo) {
		this.tipo = tipo;
	}

	public Date getData_gravacao() {
		return data_gravacao;
	}

	public void setData_gravacao(Date data_gravacao) {
		this.data_gravacao = data_gravacao;
	}	

}
