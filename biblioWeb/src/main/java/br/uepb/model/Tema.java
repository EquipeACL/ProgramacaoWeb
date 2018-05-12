package br.uepb.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.jpaEntity.EntityTema;

/**
 * Classe utilizada como modelo para um objeto do tipo Tema.
 * A classe contém  os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */

public class Tema implements IFDependencia{
	
	private int id;
	
	@NotBlank(message=" Nome é obrigatório.")
	private String nome;	
	
	@NotNull(message=" Area é obrigatório.")
	private AreaConhecimento area;
	
	private String areaConhecimento_id;
	
	/**
	 * Método construtor da classe Tema
	 * Construtor vazio (utilizado para criar um objeto do tipo Tema sem parametros definidos)
	 */
	public Tema(){
		
	}
	
	/**
	 * Método construtor da classe Tema (utilizado para criar um objeto do tipo Tema com parametros definidos)
	 * @param id id do tema
	 * @param nome nome do tema
	 * @param area objeto do tipo Area, referente ao tema
	 */
	public Tema(int id, String nome,AreaConhecimento area) {
		setId(id);
		setNome(nome);
		setArea(area);
	}
	
	public Tema(EntityTema entity) {
		setId(entity.getId());
		setNome(entity.getNome());
		setArea(new AreaConhecimento(entity.getArea()));
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

	public String getAreaConhecimento_id() {
		return areaConhecimento_id;
	}

	public void setAreaConhecimento_id(String areaConhecimento_id) {
		this.areaConhecimento_id = areaConhecimento_id;
		AreaConhecimento a = new AreaConhecimento();
		a.setId(Integer.parseInt(areaConhecimento_id));
		setArea(a);
	}

	public boolean validaDependencia() {
		return true;
	}
}
