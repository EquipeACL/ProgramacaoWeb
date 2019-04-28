package br.edu.ufab.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
/**
 * Classe que representa uma entidade do tipo Editora.
 * Como estamos usando hibernate, a classe Editora será uma entidade no banco de dados e  seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * 
 * */
@Entity
public class Editora {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message=" Nome da editora é obrigatório")
	private String nome;
	
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
}
