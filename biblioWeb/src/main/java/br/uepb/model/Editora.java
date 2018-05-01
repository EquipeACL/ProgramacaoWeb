package br.uepb.model;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Editora.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Editora implements IFDependencia{
	
	private int id;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	/**
	 * M�todo construtor da classe Editora
	 * Construtor vazio (utilizado para criar um objeto do tipo Editora sem par�metros definidos)
	 */
	public Editora() {

	}
	
	public Editora(String nome){
		setNome(nome);
	}
	
	/**
	 * M�todo construtor da classe Editora (utilizado para criar um objeto do tipo Editora com par�metros definidos)
	 * @param id, id da editora
	 * @param nome, nome da editora
	 */
	public Editora(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean validaDependencia() {
		return true;
	}
	
	

}
