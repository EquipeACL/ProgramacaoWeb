package br.uepb.model.acervo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Editora;
import br.uepb.model.Tema;

/**
 * Classe utilizada como modelo para um objeto do tipo Livro.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe Livro extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */
public class Livro extends ItemAcervo implements IFAcervo{
	
	@NotNull(message=" ISBN é obrigatório")
	private int isbn;
	
	private List<Autor> autores;
	
	private Editora editora;
	
	@NotNull(message=" Numero de paginas é obrigatório")
	private int numero_paginas;
	
	private Tema tema;
	
	@NotBlank(message = " Pelo menos um autor é obrigatório")
	private String id_autor;
	
	@NotBlank(message = " Tema é obrigatório")
	private String id_tema;
	
	@NotBlank(message = " Editora é obrigatório")
	private String id_editora;
	
	@NotBlank(message = " Data é obrigatório")
	private String string_data;
	
	/**
	 * Método construtor da classe Livro
	 * Construtor vazio (utilizado para criar um objeto do tipo Livro sem parametros definidos)
	 */
	public Livro() {		
	}
	
	/**
	 * Método construtor da classe Livro (utilizado para criar um objeto do tipo Livro com parametros definidos)
	 * @param isbn isbn do livro
	 * @param titulo titulo do livro
	 * @param autores array de objetos do tipo Autor com os autores do livro
	 * @param editora objeto do tipo Editora referente ao livro
	 * @param ano_publicacao ano de publicacao do livro
	 * @param edicao edicao do livro
	 * @param numero_paginas numero de paginas do livro
	 * @param area objeto do tipo AreaConhecimento referente ao livro
	 */
	public Livro(int isbn, String titulo, ArrayList<Autor> autores, Editora editora, Date ano_publicacao, int edicao, int numero_paginas, Tema tema) {
		setIsbn(isbn);
		setTitulo(titulo);
		setAutores(autores);
		setEditora(editora);
		setData(ano_publicacao);
		setEdicao(edicao);
		setNumero_paginas(numero_paginas);
		setTema(tema);
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
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
	
	
	public String getId_autor() {
		return id_autor;
	}

	public void setId_autor(String id_autor) {
		this.id_autor = id_autor;
	}

	public String getId_editora() {
		return id_editora;
	}

	public void setId_editora(String id_editora) {
		this.id_editora = id_editora;
	}

	public String getString_data() {
		return string_data;
	}

	public void setString_data(String string_data) {
		this.string_data = string_data;
	}
	
	public String getId_tema() {
		return id_tema;
	}

	public void setId_tema(String id_tema) {
		this.id_tema = id_tema;
	}

	public boolean validaItem() {
		return true;
	}
	
}
