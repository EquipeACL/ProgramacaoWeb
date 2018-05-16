package br.uepb.model.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.Orientador;

/**
 * Classe utilizada para fazer o mapeamento da classe Orientador para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

@Entity
@Table(name = "orientador")
public class EntityOrientador implements IFDependencia{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String formacao;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto do tipo Orientador sem parametros definidos)
	 */
	public EntityOrientador(){		
		
	}
	
	/**
	 * Método construtor da classe(utilizado para criar um objeto com parametros definidos)
	 * @param id id do orientador
	 * @param nome nome do orientador
	 * @param formacao formacao do orientado
	 */
	public EntityOrientador(Orientador orientador){
		setId(orientador.getId());
		setNome(orientador.getNome());
		setFormacao(orientador.getFormacao());
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
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public boolean validaDependencia() {
		if(this.nome!="" && this.formacao != ""){
			return true;
		}
		return false;
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
		Orientador other = (Orientador) obj;
		if (id != other.getId())
			return false;
		return true;
	}	
	
	
}
