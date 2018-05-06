package br.uepb.model.usuarios;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Essa classe � utilizada como modelo para um objeto do tipo Usuario;
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * Essa classe � a super classe que os usuarios do sistema herdam seus m�todos e atributos, que s�o comuns a todos.
 * @author EquipeACL
 */
//@MappedSuperclass
public abstract class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = " CPF é obrigatório")
	protected int cpf;
	
	@NotBlank(message = " O nome é obrigatório")
	protected String nomeCompleto;
	
	@NotEmpty(message = " RG é obrigatório")
	protected int rg;
	
	@NotBlank(message = " A naturalidade é obrigatória")
	protected String naturalidade;
	
	@NotBlank(message = " O endereço é obrigatório")
	protected String endereco;
	
	@NotEmpty(message = " O telefone é obrigatório")
	protected int telefone;
	
	@Size(min = 5, max = 20, message = " O tamanho do email deve estar entre 5 e 20")
	@NotBlank(message = " O email é obrigatório")
	protected String email;
	
	@NotBlank(message = " A senha é obrigatória")
	protected String senhaAcesso;
	
	
	/**
	 * M�todo Construtor da classe Usu�rio
	 * Construtor vazio (utilizado para criar um objeto do tipo Usuario sem par�metros definidos)
	 */
	public Usuario() { 
		
	}
	
	/**
	 * M�todo Construtor da classe Usu�rio
	 * @param cpf, n�mero do cpf do Usu�rio
	 * @param nomeCompleto, nome completo do Usu�rio
	 * @param rg, numero do rg do Usu�rio
	 * @param naturalidade, cidade natal do Usu�rio
	 * @param endereco, endere�o completo do Usu�rio
	 * @param telefone, telefone de contato do Usu�rio
	 * @param email, endere�o de email do Usu�rio
	 * @param senhaAcesso, senha de acesso ao sistema do Usu�rio
	 */
	public Usuario(int cpf, String nomeCompleto, int rg, String naturalidade, String endereco, int telefone,
			String email, String senhaAcesso) {
		setCpf(cpf);
		setNomeCompleto(nomeCompleto);
		setRg(rg);
		setNaturalidade(naturalidade);
		setEndereco(endereco);
		setTelefone(telefone);
		setEmail(email);
		setSenhaAcesso(senhaAcesso);
		
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getCpf() {
		return cpf;
	}
	
	public void setCpf(int cpf) {
		this.cpf = cpf;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public int getRg() {
		return rg;
	}
	public void setRg(int rg) {
		this.rg = rg;
	}
	public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getTelefone() {
		return telefone;
	}
	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenhaAcesso() {
		return senhaAcesso;
	}
	public void setSenhaAcesso(String senhaAcesso) {
		this.senhaAcesso = senhaAcesso;
	}
	
}
