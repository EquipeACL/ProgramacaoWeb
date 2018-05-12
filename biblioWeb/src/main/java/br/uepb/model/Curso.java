package br.uepb.model;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.enums.Tipo_curso;

/**
 * Essa classe utilizada como modelo para um objeto do tipo Curso.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class Curso implements IFDependencia{
	
	private int id;
	
	private String nome;
	
	private String sigla;	

	private AreaConhecimento area;
	
	private String area_conhecimento_id;
	
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
