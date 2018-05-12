package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Editora;
/**
 * Classe  utilizada como modelo para um objeto do tipo Revista.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe Revista extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */
public class Revista extends ItemAcervo implements IFAcervo{
		
	private Editora editora;
	
	@NotNull
	@Min(value = 1, message=" Numero de paginas deve ser maior ou igual a 1.")
	private int num_pag;
	
	@NotBlank(message="Editora obrigatória")
	private String id_editora;
	
	@NotBlank(message="Data obrigatória")
	private String data_string;
	/**
	 * Método construtor da classe Revista
	 * Construtor vazio (utilizado para criar um objeto do tipo Revista sem parametros definidos)
	 */
	public Revista() {		
	}
	/**
	 * Método construtor da classe Revista (utilizado para criar um objeto do tipo Revista com parametros definidos)
	 * @param titulo, titulo da revista
	 * @param editora objeto do tipo Editora referente a revista
	 * @param dataDePublicacao data de publicacao da revista
	 * @param edicao edicao da revista
	 * @param numeroDePaginas numero de paginas da revista
	 */
	public Revista(String titulo, Editora editora, Date dataDePublicacao, int edicao, int numeroDePaginas) {
		setTitulo(titulo);
		setEditora(editora);
		setData(dataDePublicacao);
		setEdicao(edicao);
		setNum_pag(numeroDePaginas);
	}
	
	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public Date getDataPublicacao() {
		return getData();
	}

	public void setDataPublicacao(Date dataDePublicacao) {
		setData(dataDePublicacao);
	}

	public int getNum_pag() {
		return num_pag;
	}

	public void setNum_pag(int numeroDePaginas) {
		this.num_pag = numeroDePaginas;
	}
	
	
	public String getId_editora() {
		return id_editora;
	}
	public void setId_editora(String id_editora) {
		this.id_editora = id_editora;
	}
	public String getData_string() {
		return data_string;
	}
	public void setData_string(String data_string) {
		this.data_string = data_string;
	}
	public boolean validaItem() {
		return true;
	}
	
}
