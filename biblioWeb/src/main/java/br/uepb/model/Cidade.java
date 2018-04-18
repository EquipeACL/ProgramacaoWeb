package br.uepb.model;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Cidade.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class Cidade {
	private int id;
	private int codigo;
	private String nome;
	private String uf;
	
	/**
	 * M�todo construtor da classe Cidade
	 * Construtor vazio (utilizado para criar um objeto do tipo Cidade sem par�metros definidos)
	 */
	public Cidade() {
		
	}
	
	/**
	 * M�todo construtor da classe Cidade (utilizado para criar um objeto do tipo Cidade com par�metros definidos)
	 * @param id, id da cidade
	 * @param codigo, codigo da cidade
	 * @param nome, nome da cidade
	 * @param uf, uni�o federativa da cidade
	 */
	public Cidade(int id, int codigo, String nome, String uf) {
		setId(id);
		setCodigo(codigo);
		setNome(nome);
		setUf(uf);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
}
