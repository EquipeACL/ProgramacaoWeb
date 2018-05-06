package br.uepb.model.acervo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Editora;
/**
 * Essa classe � utilizada como modelo para um objeto do tipo Revista.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Revista implementa a interface Acervo
 * @author EquipeACL
 */
@Entity
@Table(name="revista")
public class Revista extends ItemAcervo implements IFAcervo{
	@Transient
	@NotBlank(message="Data obrigatória")
	private String data_string;
	
	@Transient
	@NotBlank(message="Editora obrigatória")
	private String id_editora;
	
	
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
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "editora_id",nullable=false)
	private Editora editora;
	
	@NotNull
	@Min(value = 1, message=" Numero de paginas deve ser maior ou igual a 1.")
	private int num_pag;
	
	/**
	 * M�todo construtor da classe Revista
	 * Construtor vazio (utilizado para criar um objeto do tipo Revista sem par�metros definidos)
	 */
	public Revista() {		
	}
	/**
	 * M�todo construtor da classe Revista (utilizado para criar um objeto do tipo Revista com par�metros definidos)
	 * @param titulo, titulo da revista
	 * @param editora, objeto do tipo Editora referente a revista
	 * @param dataDePublicacao, data de publicacao da revista
	 * @param edicao, edicao da revista
	 * @param numeroDePaginas, numero de paginas da revista
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
	public boolean validaItem() {
		return true;
	}
	
}
