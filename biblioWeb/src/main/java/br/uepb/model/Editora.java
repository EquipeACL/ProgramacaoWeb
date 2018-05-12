package br.uepb.model;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.jpaEntity.EntityEditora;

/**
 * Classe utilizada como modelo para um objeto do tipo Editora.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Editora implements IFDependencia{
	
	private int id;

	private String nome;
	
	
	/**
	 * Método construtor da classe Editora
	 * Construtor vazio (utilizado para criar um objeto do tipo Editora sem parametros definidos)
	 */
	public Editora() {

	}
	
	public Editora(String nome){
		setNome(nome);
	}
	
	/**
	 * Método construtor da classe Editora (utilizado para criar um objeto do tipo Editora com parametros definidos)
	 * @param id id da editora
	 * @param nome nome da editora
	 */
	public Editora(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public Editora(EntityEditora entity) {
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
		Editora other = (Editora) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
