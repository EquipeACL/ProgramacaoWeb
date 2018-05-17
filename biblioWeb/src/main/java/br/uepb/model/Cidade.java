package br.uepb.model;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.model.jpaEntity.EntityCidade;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Cidade.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Cidade {
	
	private int id;
	
	
	private int codigo;
	
	@NotBlank(message = " Nome da cidade é obrigatório")
	private String nome;
	
	@NotBlank(message = " Código da cidade é obrigatório")
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
	
	public Cidade(EntityCidade cidade) {
		setId(cidade.getId());
		setCodigo(cidade.getCodigo());
		setNome(cidade.getNome());
		setUf(cidade.getUf());
	}
	
	public Cidade(int id, int codigo, String nome, String uf) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
