package br.uepb.model;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.jpaEntity.EntityAreaConhecimento;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo AreaConhecimento.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class AreaConhecimento implements IFDependencia{
	
	
	private int id;
	
	private String nome;
	
	/**
	 * Método construtor da classe AreaConhecimento
	 * Construtor vazio (utilizado para criar um objeto do tipo AreaConhecimento sem parametros definidos)
	 */
	public AreaConhecimento() {
		
	}
	
	/**
	 * Método construtor da classe AreaConhecimento (utilizado para criar um objeto do tipo AreaConhecimento com parametros definidos)
	 * @param id id da area do conhecimento
	 * @param nome nome da area do conhecimento
	 */
	public AreaConhecimento(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public AreaConhecimento(EntityAreaConhecimento entity) {
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

}
