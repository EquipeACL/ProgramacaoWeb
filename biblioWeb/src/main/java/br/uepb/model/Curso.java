package br.uepb.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.enums.Tipo_curso;
import br.uepb.model.jpaEntity.EntityCurso;

/**
 * Essa classe utilizada como modelo para um objeto do tipo Curso.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class Curso implements IFDependencia{
	
	private int id;
	
	@NotBlank(message=" Nome é obrigatório")
	private String nome;
	
	@NotBlank(message=" Sigla do curso é obrigatório")
	private String sigla;	

	@NotNull(message=" Area do conhecimento é obrigatório")
	private AreaConhecimento area;
	
	@NotNull(message=" Tipo do curso é obrigatório")
	private Tipo_curso tipo;
	
	/**
	 * Método construtor da classe Curso
	 * Construtor vazio (utilizado para criar um objeto do tipo Curso sem parametros definidos)
	 */
	public Curso(){
		
	}
	
	/**
	 * Método construtor da classe Curso (utilizado para criar um objeto do tipo Curso com parametros definidos)
	 * @param nome nome do curso
	 * @param sigla sigla do curso
	 * @param area objeto do tipo Area referente ao curso
	 * @param tipo Enum Tipo_curso, referente ao tipo do curso
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
	
	public Curso(EntityCurso curso){
		setId(curso.getId());
		setNome(curso.getNome());
		setSigla(curso.getSigla());
		setArea(new AreaConhecimento(curso.getArea()));
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
