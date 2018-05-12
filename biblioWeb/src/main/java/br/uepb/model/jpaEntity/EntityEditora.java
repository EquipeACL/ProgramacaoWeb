package br.uepb.model.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.Editora;

/**
 * Classe utilizada para fazer o mapeamento da classe Editora para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name = "editora")
public class EntityEditora implements IFDependencia{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String nome;
	
	
	//private List<Livro> livros;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem parametros definidos)
	 */
	public EntityEditora() {

	}
	
	public EntityEditora(Editora editora){
		setId(editora.getId());
		setNome(editora.getNome());
	}
	
	/**
	 * Método construtor da classe (utilizado para criar um objeto com parametros definidos)
	 * @param id id da editora
	 * @param nome nome da editora
	 */
	public EntityEditora(int id, String nome) {
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
		Editora other = (Editora) obj;
		if (id != other.getId())
			return false;
		return true;
	}
	
	
	

}
