package br.uepb.model.jpaEntity.usuarios;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.jpaEntity.EntityCurso;
import br.uepb.model.usuarios.Aluno;
/**
 * Essa classe � utilizada como modelo para um objeto do tipo Aluno;
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Aluno extende a classe Usu�rio, que cont�m os atributos e m�todos comuns a todos os usu�rios do sistema.
 * @author EquipeACL
 */
@Entity
@Table(name="aluno")
public class EntityAluno extends EntityUsuario {
	
	private String matricula;
	
	@NotBlank(message=" Nome da mãe é obrigatório")
	private String nomeMae;
	
	@NotNull(message=" Curso é obrigatório")
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "curso_id",nullable=false)
	private EntityCurso curso;
	

	@NotNull(message=" Nivel do aluno é obrigatório")
	@Enumerated(EnumType.STRING)
	private Tipo_nivel nivel;
	
	@NotNull(message=" Data de ingresso é obrigatório")
	private Date anoIngresso;
	
	@NotNull(message=" Periodo de ingresso é obrigatório")
	private int periodoIngresso;

	/**
	 * M�todo construtor da classe Aluno
	 * Construtor vazio (utilizado para criar um objeto do tipo Aluno sem par�metros definidos)
	 */
	public EntityAluno() {	

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
	public EntityAluno(String matricula, String cpf, String rg, String naturalidade, String nomeCompleto, String nomeMae,
			String endereco, String telefone, EntityCurso curso, Tipo_nivel nivel, String email, Date anoIngresso,
			int periodoIngresso, String senhaAcesso,String senhaConfirmacao) {
		super(cpf, nomeCompleto, rg, naturalidade, endereco, telefone, email, senhaAcesso,senhaConfirmacao);
		setMatricula(matricula);
		setNomeMae(nomeMae);
		setEntityCurso(curso);
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
	public EntityAluno(Aluno aluno) {
		super(aluno.getCpf(), aluno.getNome(), aluno.getRg(), aluno.getNaturalidade(), aluno.getEndereco(), aluno.getTelefone(), aluno.getEmail(), aluno.getSenha(),aluno.getConfirmacaoSenha());
		setId(aluno.getId());
		setNomeMae(aluno.getNomeMae());
		setEntityCurso(new EntityCurso(aluno.getCurso()));
		setNivel(aluno.getNivel());
		setAnoIngresso(aluno.getAnoIngresso());
		setPeriodoIngresso(aluno.getPeriodoIngresso());
		setMatricula(aluno.getMatricula());
		
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
	public EntityCurso getEntityCurso() {
		return curso;
	}
	public void setEntityCurso(EntityCurso curso) {
		this.curso = curso;
	}
	public Tipo_nivel getNivel() {
		return nivel;
	}
	public void setNivel(Tipo_nivel nivel) {
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
	
	public EntityCurso getCurso() {
		return curso;
	}
	public void setCurso(EntityCurso curso) {
		this.curso = curso;
	}
	
	
}

