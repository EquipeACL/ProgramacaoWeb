package br.edu.ufab.model.entities.pessoas;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
/**
 * Classe abstrata que pega todos os dados da Pessoa.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pessoa {

	@Id
	@GeneratedValue
	private long id;
	
	@NotBlank(message=" CPF é obrigatório")
	private String cpf;
	
	@NotBlank(message=" RG é obrigatório")
	private String rg;
	
	@NotBlank(message=" Nome é obrigatório")
	private String nome;
	
	@NotBlank(message=" Naturaliade é obrigatório")
	private String naturalidade;
	
	@NotBlank(message=" Endereço é obrigatório")
	private String endereco;
	
	@NotBlank(message=" Telefone é obrigatório")
	private String telefone;
	
	@NotBlank(message=" Data de nascimento é obrigatório")
	private Date datanascimento;
	
	@NotBlank(message=" Senha é obrigatório")
	private String senha;
	
	@NotBlank(message=" Email é obrigatório")
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
