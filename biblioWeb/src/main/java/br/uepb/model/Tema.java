package br.uepb.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Tema.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="tema")
public class Tema implements IFDependencia{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message=" Nome é obrigatório.")
	private String nome;
	
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "areaconhecimento_id",nullable=false)
	private AreaConhecimento area;
	
	@Transient
	@NotBlank(message=" Area do conhecimento é obrigatório")
	private String areaconhecimento_id;
	
	/**
	 * M�todo construtor da classe Tema
	 * Construtor vazio (utilizado para criar um objeto do tipo Tema sem par�metros definidos)
	 */
	public Tema(){
		
	}
	
	/**
	 * M�todo construtor da classe Tema (utilizado para criar um objeto do tipo Tema com par�metros definidos)
	 * @param id, id do tema
	 * @param nome, nome do tema
	 * @param area, objeto do tipo Area, referente ao tema
	 */
	public Tema(int id, String nome,AreaConhecimento area) {
		setId(id);
		setNome(nome);
		setArea(area);
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
	
	public AreaConhecimento getArea() {
		return area;
	}

	public void setArea(AreaConhecimento area) {
		this.area = area;
	}
	
	public String getAreaconhecimento_id() {
		return areaconhecimento_id;
	}

	public void setAreaconhecimento_id(String areaconhecimento_id) {
		this.areaconhecimento_id = areaconhecimento_id;
	}

	public boolean validaDependencia() {
		return true;
	}
}
