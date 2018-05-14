package br.uepb.model.jpaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;

/**
 * Classe utilizada para fazer o mapeamento da classe Curso para a base de dados.
 * A classe contém  os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="curso")
public class EntityCurso implements IFDependencia{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	
	
	private String sigla;	

	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "area_conhecimento_id",nullable=false)
	private EntityAreaConhecimento area;
	
	@Enumerated(EnumType.STRING)
	private Tipo_curso tipo;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem parametros definidos)
	 */
	public EntityCurso(){
		
	}
	
	/**
	 * Método construtor da classe (utilizado para criar um objeto com parametros definidos)
	 * @param nome nome do curso
	 * @param sigla sigla do curso
	 * @param area objeto do tipo Area referente ao curso
	 * @param tipo Enum Tipo_curso, referente ao tipo do curso
	 */
	public EntityCurso(String nome, String sigla, EntityAreaConhecimento area, Tipo_curso tipo) {
		setNome(nome);
		setSigla(sigla);
		setArea(area);
		setTipo(tipo);
	}
	public EntityCurso(Curso curso) {
		setId(curso.getId());
		setNome(curso.getNome());
		setSigla(curso.getSigla());
		setArea(new EntityAreaConhecimento(curso.getArea()));
		setTipo(curso.getTipo());
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
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public EntityAreaConhecimento getArea() {
		return area;
	}
	public void setArea(EntityAreaConhecimento area) {
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
