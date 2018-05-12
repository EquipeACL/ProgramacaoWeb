package br.uepb.model.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.Autor;

/**
 * Essa classe utilizada para fazer o mapeamento da classe Autor para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

@Entity
@Table(name = "autor")
public class EntityAutor implements IFDependencia{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = " Nome do Autor é obrigatório")
	private String nome;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem parametros definidos)
	 */
	public EntityAutor(){
	}
	
	
	/**
	 * Método construtor da classe (utilizado para criar um objeto com parametros definidos)
	 * @param id id do autor
	 * @param nome nome do autor
	 */
	public EntityAutor(Autor autor) {
		setId(autor.getId());
		setNome(autor.getNome());
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
		if (id != other.getId())
			return false;
		return true;
	}	
	
	
	
}
