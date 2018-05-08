package br.uepb.model.acervo;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.NotEmpty;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Editora;
import br.uepb.model.Tema;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Livro.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Livro estende a classe ItemAcervo
 * @author EquipeACL
 */
/*@Entity
@Table(name="livro")*/
public class Livro extends ItemAcervo implements IFAcervo{
	
	
	@NotEmpty(message=" ISBN é obrigatório")
	private long isbn;
	
	//@ManyToMany
	private ArrayList<Autor> autores;
	
	
	private Editora editora;
	
	@Transient
	@NotNull(message = " Editora é obrigatório")
	private String id_editora;
	
	@Transient
	@NotNull(message = " Data é obrigatório")
	private String string_data;
	
	@NotEmpty(message=" Numero de paginas é obrigatório")
	private int numero_paginas;
	
	private Tema tema;
	
	@Transient
	@NotNull(message = " Tema é obrigatório")
	private String id_tema;
	
	/**
	 * M�todo construtor da classe Livro
	 * Construtor vazio (utilizado para criar um objeto do tipo Livro sem par�metros definidos)
	 */
	public Livro() {		
	}
	
	/**
	 * M�todo construtor da classe Livro (utilizado para criar um objeto do tipo Livro com par�metros definidos)
	 * @param isbn, isbn do livro
	 * @param titulo, titulo do livro
	 * @param autores, array de objetos do tipo Autor com os autores do livro
	 * @param editora, objeto do tipo Editora referente ao livro
	 * @param ano_publicacao, ano de publicacao do livro
	 * @param edicao, edicao do livro
	 * @param numero_paginas, numero de paginas do livro
	 * @param area, objeto do tipo AreaConhecimento referente ao livro
	 */
	public Livro(long isbn, String titulo, ArrayList<Autor> autores, Editora editora, Date ano_publicacao, int edicao, int numero_paginas, Tema tema) {
		setIsbn(isbn);
		setTitulo(titulo);
		setAutores(autores);
		setEditora(editora);
		setData(ano_publicacao);
		setEdicao(edicao);
		setNumero_paginas(numero_paginas);
		setTema(tema);
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public ArrayList<Autor> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<Autor> autores) {
		this.autores = autores;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	
	public Date getAnoPublicacao(){
		return getData();
	}
	
	public void setAnoPublicacao(Date anoPublicacao){
		setData(anoPublicacao);
	}

	public int getNumero_paginas() {
		return numero_paginas;
	}

	public void setNumero_paginas(int numero_paginas) {
		this.numero_paginas = numero_paginas;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}
	
	
	public String getId_editora() {
		return id_editora;
	}

	public void setId_editora(String id_editora) {
		this.id_editora = id_editora;
	}

	public String getId_tema() {
		return id_tema;
	}

	public void setId_tema(String id_tema) {
		this.id_tema = id_tema;
	}
	
	
	public String getString_data() {
		return string_data;
	}

	public void setString_data(String string_data) {
		this.string_data = string_data;
	}

	public boolean validaItem() {
		return true;
	}
	
}
