package br.edu.ufab.model.entities.itens;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ufab.model.entities.Editora;
/**
 * Classe sse abstrata que gera os campos de edicao,editora e numero de paginas.
 * Esta classe herda de ItemAcervo, pois Impresso � um tipo de Item do Acervo.
 * 
 * @author Murilo Gustavo e Taynar Sousa 
 * 
 * Sprint3-18/05/2018
 * */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Impresso extends ItemAcervo {

	@NotBlank(message=" Edição do Impresso é obrigatório")
	private String edicao;
	
	@NotNull(message=" Editora é obrigatório")
	@ManyToOne
	private Editora editora;
	
	@NotNull(message=" Numero de paginas é obrigatório")
	private int numpaginas;


	public String getEdicao() {
		return edicao;
	}


	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}


	public Editora getEditora() {
		return editora;
	}


	public void setEditora(Editora editora) {
		this.editora = editora;
	}


	public int getNumpaginas() {
		return numpaginas;
	}


	public void setNumpaginas(int numpaginas) {
		this.numpaginas = numpaginas;
	}
}
