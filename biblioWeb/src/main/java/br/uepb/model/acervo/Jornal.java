package br.uepb.model.acervo;

import java.util.Date;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Jornal.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Jornal estende a classe ItemAcervo
 * @author EquipeACL
 */
public class Jornal extends ItemAcervo{
	
	private int id;
	
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


}
