package br.uepb.model.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.uepb.model.Cidade;

@Entity
@Table(name="cidade")
public class EntityCidade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private int codigo;
	
	private String nome;
	
	private String uf;
	
	/**
	 * M�todo construtor da classe Cidade
	 * Construtor vazio (utilizado para criar um objeto do tipo Cidade sem par�metros definidos)
	 */
	public EntityCidade() {
		
	}
	
	/**
	 * M�todo construtor da classe Cidade (utilizado para criar um objeto do tipo Cidade com par�metros definidos)
	 * @param id, id da cidade
	 * @param codigo, codigo da cidade
	 * @param nome, nome da cidade
	 * @param uf, uni�o federativa da cidade
	 */
	public EntityCidade(Cidade cidade) {
		setId(cidade.getId());
		setCodigo(cidade.getCodigo());
		setNome(cidade.getNome());
		setUf(cidade.getUf());
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
		if (id != other.getId())
			return false;
		return true;
	}
	

}
