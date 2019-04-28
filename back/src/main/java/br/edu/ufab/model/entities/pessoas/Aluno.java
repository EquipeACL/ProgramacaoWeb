package br.edu.ufab.model.entities.pessoas;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.enums.PeriodoDeIngresso;
import br.edu.ufab.model.enums.TipoDeCurso;
/**
 * Classe que representa uma entidade do tipo Aluno.
 * Como estamos usando hibernate, a classe Aluno será uma entidade no banco de dados e  seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
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
	
	public void gerarMatricula(){
		//Monta matricua
		Curso curso = this.getCurso();
		TipoDeCurso tipo = curso.getTipo();
		String codigo = curso.getCodigo();
		String anointeiro = String.valueOf(this.getAno());
		String ano = anointeiro.substring(2,4);
		
		PeriodoDeIngresso periodoenum = this.getPeriodo();
		String periodo = "";
		if(periodoenum.toString() == "PRIMEIRO") {
			periodo = "1";
		} else {
			periodo = "2";
		}
		
		Random gerador = new Random();
		int aux = gerador.nextInt(1000);
		
		String num = Integer.toString(aux);
		
		String id = "";
		if(num.length() == 1) {
			id = "00"+num;
		} else if (num.length() == 2) {
			id = "0"+num;
		} else {
			id = num;
		}
		
		String matricula = tipo.getDescricao().charAt(0)+codigo+"-"+ano+periodo+id;
		this.setMatricula(matricula);
	}
}
