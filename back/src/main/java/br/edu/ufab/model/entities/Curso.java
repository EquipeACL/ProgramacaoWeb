package br.edu.ufab.model.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.enums.AreaDeCurso;
import br.edu.ufab.model.enums.TipoDeCurso;
/**
 * Classe que representa uma entidade do tipo Curso.
 * Como estamos usando hibernate, a classe Curso  será uma entidade no banco de dados e seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa
 * @author Alterações por: EquipeACL 
 * 
 * 
 * */
@Entity
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message=" Nome do curso é obrigatório")
	private String nome;
	
	@NotBlank(message=" Codigo do curso é obrigatório")
	private String codigo;
	
	@NotNull(message=" Area do curso é obrigatório")
	@Enumerated(EnumType.STRING)
	private AreaDeCurso area;
	
	@NotNull(message=" Tipo do curso é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeCurso tipo;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public AreaDeCurso getArea() {
		return area;
	}

	public void setArea(AreaDeCurso area) {
		this.area = area;
	}

	public TipoDeCurso getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeCurso tipo) {
		this.tipo = tipo;
	}
}
