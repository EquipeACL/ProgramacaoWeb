package br.uepb.model;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.jpaEntity.EntityAutor;

/**
 * Classe utilizada como modelo para um objeto do tipo Autor.
 * A classe contém  os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Autor implements IFDependencia{
	
	private int id;

	private String nome;
	
	public Autor(){
	}
	/**
	 * Método construtor da classe Autor
	 * Construtor vazio (utilizado para criar um objeto do tipo Autor sem parametros definidos)
	 */
	public Autor(String nome){
		setNome(nome);
	}
	
	/**
	 * Método construtor da classe Autor (utilizado para criar um objeto do tipo Autor com parametros definidos)
	 * @param id id do autor
	 * @param nome nome do autor
	 */
	public Autor(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public Autor(EntityAutor entity) {
		setId(entity.getId());
		setNome(entity.getNome());
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
