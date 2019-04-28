package br.edu.ufab.model.entities.itens;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.enums.TipoDeAnais;
/**
 * Classe que representa uma entidade do tipo Anais.
 * Como estamos usando hibernate, a classe Anais será uma entidade no banco de daods e seus atributos serão os campos
 * que serão gerados, conforme mostramos abaixo,
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * @author Alterações por: EquipeACL
 * 
 * */
@Entity
public class Anais extends TrabalhoAcademico {
	
	@NotNull(message = " Tipo de Anal é obrigatório")
	@Enumerated(EnumType.STRING)
	private TipoDeAnais tipo;

	@NotBlank(message = " Nome do congresso é obrigatório")
	private String nomecongreco;

	public TipoDeAnais getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeAnais tipo) {
		this.tipo = tipo;
	}

	public String getNomecongreco() {
		return nomecongreco;
	}

	public void setNomecongreco(String nomecongreco) {
		this.nomecongreco = nomecongreco;
	}
}
