package br.uepb.model.jpaEntity.usuarios;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import br.uepb.model.Grupo;
import br.uepb.validation.AtributoConfirmacao;


/**
 * Essa classe � utilizada como modelo para um objeto do tipo Usuario;
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * Essa classe � a super classe que os usuarios do sistema herdam seus m�todos e atributos, que s�o comuns a todos.
 * @author EquipeACL
 */
@MappedSuperclass
@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha",message = "Senhas não conferem")
public abstract class EntityUsuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = " CPF é obrigatório")
	protected String cpf;
	
	@NotBlank(message = " O nome é obrigatório")
	protected String nome;
		
	@NotBlank(message = " RG é obrigatório")
	protected String rg;
	
	@NotBlank(message = " A naturalidade é obrigatória")
	protected String naturalidade;
	
	@NotBlank(message = " O endereço é obrigatório")
	protected String endereco;
	
	@NotBlank(message = " O telefone é obrigatório")
	protected String telefone;
	
	@Size(min = 5, max = 45, message = " O tamanho do email deve estar entre 5 e 20")
	@NotBlank(message = " O email é obrigatório")
	protected String email;
	
	@NotBlank(message = " A senha é obrigatória")
	protected String senha;
	
	@Transient
	protected String confirmacaoSenha;
	
	
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Size(min=1,message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "aluno_has_grupo",joinColumns = @JoinColumn(name = "aluno_id")
												, inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List <Grupo> grupos;
	
	
/*	@NotNull(message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "usuario_has_grupo",joinColumns = @JoinColumn(name = "usuario_id")
												, inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List <Grupo> grupos;*/
	
	/**
	 * M�todo Construtor da classe Usu�rio
	 * Construtor vazio (utilizado para criar um objeto do tipo Usuario sem par�metros definidos)
	 */
	public EntityUsuario() { 
		
	}
	
	/**
	 * M�todo Construtor da classe Usu�rio
	 * @param cpf, n�mero do cpf do Usu�rio
	 * @param nome, nome completo do Usu�rio
	 * @param rg, numero do rg do Usu�rio
	 * @param naturalidade, cidade natal do Usu�rio
	 * @param endereco, endere�o completo do Usu�rio
	 * @param telefone, telefone de contato do Usu�rio
	 * @param email, endere�o de email do Usu�rio
	 * @param senha, senha de acesso ao sistema do Usu�rio
	 */
	public EntityUsuario(String cpf, String nome, String rg, String naturalidade, String endereco, String telefone,
			String email, String senha,String confirmacaoSenha) {
		setCpf(cpf);
		setNome(nome);
		setRg(rg);
		setNaturalidade(naturalidade);
		setEndereco(endereco);
		setTelefone(telefone);
		setEmail(email);
		setSenha(senha);
		setConfirmacaoSenha(confirmacaoSenha);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityUsuario other = (EntityUsuario) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}



	

}
