package br.uepb.model;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo AreaConhecimento.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class AreaConhecimento {
	private int id;
	private String nome;
	
	/**
	 * M�todo construtor da classe AreaConhecimento
	 * Construtor vazio (utilizado para criar um objeto do tipo AreaConhecimento sem par�metros definidos)
	 */
	public AreaConhecimento() {
		
	}
	
	/**
	 * M�todo construtor da classe AreaConhecimento (utilizado para criar um objeto do tipo AreaConhecimento com par�metros definidos)
	 * @param id, id da �rea do conhecimento
	 * @param nome, nome da �rea do conhecimento
	 */
	public AreaConhecimento(int id, String nome) {
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
