package br.uepb.model.jpaEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.AreaConhecimento;

/**
 * Classe utilizada para fazer o mapeamento da classe AreaCOnhecimento para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name = "area_conhecimento")
public class EntityAreaConhecimento implements IFDependencia{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String nome;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem parametros definidos)
	 */
	public EntityAreaConhecimento() {
		
	}
	
	/**
	 * Método construtor da classe (utilizado para criar um objeto com par�metros definidos)
	 * @param id id da area do conhecimento
	 * @param nome nome da area do conhecimento
	 */
	public EntityAreaConhecimento(AreaConhecimento area) {
		if(area!=null){
			setId(area.getId());
			setNome(area.getNome());
		}
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
