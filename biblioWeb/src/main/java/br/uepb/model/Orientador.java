package br.uepb.model;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.jpaEntity.EntityOrientador;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Orientador.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Orientador implements IFDependencia{
	
	private int id;
	
	@NotBlank(message = " Nome do Orientador é obrigatório!")
	private String nome;
	
	@NotBlank(message = " Formação é obrigatória !")
	private String formacao;
	
	/**
	 * M�todo construtor da classe Orientador
	 * Construtor vazio (utilizado para criar um objeto do tipo Orientador sem par�metros definidos)
	 */
	public Orientador(){		
		
	}
	
	/**
	 * M�todo construtor da classe Orientador (utilizado para criar um objeto do tipo Orientador com par�metros definidos)
	 * @param id, id do orientador
	 * @param nome, nome do orientador
	 * @param formacao, formacao do orientado
	 */
	public Orientador(int id,String nome, String formacao){
		setId(id);
		setNome(nome);
		setFormacao(formacao);
	}
	
	public Orientador(EntityOrientador entity){
		setId(entity.getId());
		setNome(entity.getNome());
		setFormacao(entity.getFormacao());
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
		Orientador other = (Orientador) obj;
		if (id != other.id)
			return false;
		return true;
	}	
	
	
}
