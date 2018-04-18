package br.uepb.model.usuarios;

import java.sql.Date;
import java.text.DateFormat;

import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_nivel_aluno;
/**
 * Essa classe � respons�vel por criar um objeto do tipo Aluno;
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Aluno extende a classe Usu�rio, que cont�m os atributos e m�todos comuns a todos os usu�rios do sistema.
 * @author EquipeACL
 */
public class Aluno extends Usuario {
	
	private String matricula;
	private String nomeMae;
	private Curso curso;
	private Tipo_nivel_aluno nivel;
	private Date anoIngresso;
	private int periodoIngresso;

	/**
	 * M�todo construtor da classe Aluno
	 * Construtor vazio (utilizado para criar um objeto do tipo Aluno sem par�metros definidos)
	 */
	public Aluno() {	
	}
	/**
	 * M�todo construtor da classe Aluno (utilizado para instanciar objetos durante a busca de um objeto do tipo Aluno no Banco de Dados)
	 * @param matricula, matr�cula do aluno
	 * @param cpf, n�mero do CPF do aluno
	 * @param rg, n�mero do RG do aluno
	 * @param naturalidade, cidade natal do aluno
	 * @param nomeCompleto, nome completo do aluno
	 * @param nomeMae, nome completo da m�e do aluno
	 * @param endereco, endere�o do aluno
	 * @param telefone, telefone para contato do aluno
	 * @param curso, Objeto do tipo Curso referente ao curso que o aluno ingressou
	 * @param nivel, Enum do nivel do aluno (se � graduando, mestrando, doutorando ou p�s-doutorando)
	 * @param email, endere�o de email do aluno
	 * @param anoIngresso, o ano de ingresso do aluno no curso
	 * @param periodoIngresso, o per�odo de ingresso do aluno no curso
	 * @param senhaAcesso, a senha de acesso ao sistema do aluno
	 */
	public Aluno(String matricula, int cpf, int rg, String naturalidade, String nomeCompleto, String nomeMae,
			String endereco, int telefone, Curso curso, Tipo_nivel_aluno nivel, String email, Date anoIngresso,
			int periodoIngresso, String senhaAcesso) {
		super(cpf, nomeCompleto, rg, naturalidade, endereco, telefone, email, senhaAcesso);
		setMatricula(matricula);
		setCurso(curso);
		setNivel(nivel);
		setAnoIngresso(anoIngresso);
		setPeriodoIngresso(periodoIngresso);
	}
	/**
	 * M�todo construtor da classe Aluno (utilizado como objeto que ser� passado por par�metro durante a inser��o de um objeto do tipo Aluno no Banco de Dados
	 * @param cpf, n�mero do CPF do aluno
	 * @param rg, n�mero do RG do aluno
	 * @param naturalidade, cidade natal do aluno
	 * @param nomeCompleto, nome completo do aluno
	 * @param nomeMae, nome completo da m�e do aluno
	 * @param endereco, endere�o do aluno
	 * @param telefone, telefone para contato do aluno
	 * @param curso, Objeto do tipo Curso referente ao curso que o aluno ingressou
	 * @param nivel, Enum do nivel do aluno (se � graduando, mestrando, doutorando ou p�s-doutorando)
	 * @param email, endere�o de email do aluno
	 * @param anoIngresso, o ano de ingresso do aluno no curso
	 * @param periodoIngresso, o per�odo de ingresso do aluno no curso
	 * @param senhaAcesso, a senha de acesso ao sistema do aluno
	 */
	public Aluno(int cpf, int rg, String naturalidade, String nomeCompleto, String nomeMae,
			String endereco, int telefone, Curso curso, Tipo_nivel_aluno nivel, String email, Date anoIngresso, int periodoIngresso,
			String senhaAcesso) {
		super(cpf, nomeCompleto, rg, naturalidade, endereco, telefone, email, senhaAcesso);

		setNomeMae(nomeMae);
		setCurso(curso);
		setNivel(nivel);
		setAnoIngresso(anoIngresso);
		setPeriodoIngresso(periodoIngresso);
		gerarMatrcula();
		
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Tipo_nivel_aluno getNivel() {
		return nivel;
	}
	public void setNivel(Tipo_nivel_aluno nivel) {
		this.nivel = nivel;
	}
	public Date getAnoIngresso() {
		return anoIngresso;
	}
	public void setAnoIngresso(Date anoIngresso) {
		this.anoIngresso = anoIngresso;
	}
	public int getPeriodoIngresso() {
		return periodoIngresso;
	}
	public void setPeriodoIngresso(int periodoIngresso) {
		this.periodoIngresso = periodoIngresso;
	}
	/**
	 * M�todo utilizaod para gerar a matr�cula do aluno, utilizando os atributos da pr�pria classe
	 */
	public void gerarMatrcula() {
		
		this.matricula = "";
		this.matricula+= this.nivel+this.curso.getSigla()+this.anoIngresso.toString().substring(2, 4)+this.periodoIngresso;
		
	}
	
}

