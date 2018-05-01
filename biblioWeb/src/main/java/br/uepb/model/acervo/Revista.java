package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Editora;
/**
 * Essa classe � utilizada como modelo para um objeto do tipo Revista.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Revista implementa a interface Acervo
 * @author EquipeACL
 */
public class Revista extends ItemAcervo implements IFAcervo{
	@NotNull(message="Editora não pode ser nula!")
	private Editora editora;
	
	@NotNull
	@Min(value = 1, message="Numero de paginas deve ser maior ou igual a 1.")
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
