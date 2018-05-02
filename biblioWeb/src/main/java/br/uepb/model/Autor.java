package br.uepb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Autor.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

@Entity
@Table(name = "autor")
public class Autor implements IFDependencia{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = " Nome do Autor é obrigatório")
	private String nome;
	
	public Autor(){
	}
	/**
	 * M�todo construtor da classe Autor
	 * Construtor vazio (utilizado para criar um objeto do tipo Autor sem par�metros definidos)
	 */
	public Autor(String nome){
		setNome(nome);
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
	public boolean validaDependencia() {
		return true;
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
		Autor other = (Autor) obj;
		if (id != other.id)
			return false;
		return true;
	}	
	
	
	
}
