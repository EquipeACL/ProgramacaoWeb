package br.uepb.model;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Autor.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class Autor {
	private int id;
	private String nome;
	
	/**
	 * M�todo construtor da classe Autor
	 * Construtor vazio (utilizado para criar um objeto do tipo Autor sem par�metros definidos)
	 */
	public Autor(){
		
	}
	
	/**
	 * M�todo construtor da classe Autor (utilizado para criar um objeto do tipo Autor com par�metros definidos)
	 * @param id, id do autor
	 * @param nome, nome do autor
	 */
	public Autor(int id, String nome) {
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
	
}
