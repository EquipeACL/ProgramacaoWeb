package br.uepb.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.persistence.EnumType;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.enums.Tipo_curso;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Curso.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="curso")
public class Curso implements IFDependencia{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message=" Nome é obrigatório")
	private String nome;
	
	@NotBlank(message=" Sigla do curso é obrigatório")
	private String sigla;	

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "area_conhecimento_id",nullable=false)
	private AreaConhecimento area;
	
	@Transient
	@NotBlank(message=" Area do conhecimento é obrigatório")
	private String area_conhecimento_id;
	
	@NotNull(message=" Tipo do curso é obrigatório")
	@Enumerated(EnumType.STRING)
	private Tipo_curso tipo;
	
	/**
	 * M�todo construtor da classe Curso
	 * Construtor vazio (utilizado para criar um objeto do tipo Curso sem par�metros definidos)
	 */
	public Curso(){
		
	}
	
	/**
	 * M�todo construtor da classe Curso (utilizado para criar um objeto do tipo Curso com par�metros definidos)
	 * @param nome, nome do curso
	 * @param sigla, sigla do curso
	 * @param area, objeto do tipo Area referente ao curso
	 * @param tipo, Enum Tipo_curso, referente ao tipo do curso
	 */
	public Curso(String nome, String sigla, AreaConhecimento area, Tipo_curso tipo) {
		setNome(nome);
		setSigla(sigla);
		setArea(area);
		setTipo(tipo);
	}
	public Curso(int id,String nome, String sigla, AreaConhecimento area, Tipo_curso tipo) {
		setId(id);
		setNome(nome);
		setSigla(sigla);
		setArea(area);
		setTipo(tipo);
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
	
	public String getArea_conhecimento_id() {
		return area_conhecimento_id;
	}

	public void setArea_conhecimento_id(String area_conhecimento_id) {
		this.area_conhecimento_id = area_conhecimento_id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public AreaConhecimento getArea() {
		return area;
	}
	public void setArea(AreaConhecimento area) {
		this.area = area;
	}
	public Tipo_curso getTipo() {
		return tipo;
	}
	public void setTipo(Tipo_curso tipo) {
		this.tipo = tipo;
	}

	public boolean validaDependencia() {
		return true;
	}

}
