package br.uepb.model.jpaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.Tema;

/**
 * Classe utilizada para fazer o mapeamento da classe Tema para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="tema")
public class EntityTema implements IFDependencia{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank
	private String nome;
	
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "areaconhecimento_id",nullable=false)
	private EntityAreaConhecimento area;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto do tipo Tema sem parametros definidos)
	 */
	public EntityTema(){
		
	}
	
	/**
	 * Método construtor da classe(utilizado para criar um objeto com parametros definidos)
	 * @param id id do tema
	 * @param nome nome do tema
	 * @param area objeto do tipo Area, referente ao tema
	 */
	public EntityTema(Tema tema) {
		if(tema!=null){
			setId(tema.getId());
			setNome(tema.getNome());
			setArea(new EntityAreaConhecimento(tema.getArea()));
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
	
	public EntityAreaConhecimento getArea() {
		return area;
	}

	public void setArea(EntityAreaConhecimento area) {
		this.area = area;
	}
	

	public boolean validaDependencia() {
		return true;
	}
}
