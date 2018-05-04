package br.uepb.model.acervo;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotNull;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Autor;
import br.uepb.model.Editora;
import br.uepb.model.Tema;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Livro.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Livro estende a classe ItemAcervo
 * @author EquipeACL
 */


public class Livro extends ItemAcervo implements IFAcervo{
	
	

	private long isbn;
	
	private ArrayList<Autor> autores;
	
	@NotNull(message = " O nome da Editora é obrigatório")
	private Editora editora;	
	
	private int numero_paginas;
	private Tema tema;
	
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

	public boolean validaItem() {
		return true;
	}
	
}
