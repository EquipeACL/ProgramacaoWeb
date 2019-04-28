package br.edu.ufab.model.entities.pessoas;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.enums.PeriodoDeIngresso;
/**
 * Classe que representa uma view dos dados de Aluno. Como estamos
 * usando hibernate, a classe aluno � uma entidade e  seus atributos ser�o os campos
 * que ser�o gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */
@Entity
public class Aluno extends Pessoa {
	
	private String matricula;
	
	@NotBlank(message=" Nome da mãe é obrigatório")
	private String nomemae;
	
	@NotNull(message=" Curso é obrigatório")
	@ManyToOne
	private Curso curso;
	
	@NotNull(message=" Ano de ingresso é obrigatório")
	private int ano;
	
	@NotNull(message=" Periodo de ingresso é obrigatório")
	@Enumerated(EnumType.STRING)
	private PeriodoDeIngresso periodo;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNomemae() {
		return nomemae;
	}

	public void setNomemae(String nomemae) {
		this.nomemae = nomemae;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public PeriodoDeIngresso getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoDeIngresso periodo) {
		this.periodo = periodo;
	}
}
