package br.uepb.model.jpaEntity.acervo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.acervo.Livro;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.EntityEditora;
import br.uepb.model.jpaEntity.EntityTema;

/**
 * Classe utilizada para fazer o mapeamento da classe Livro para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */
@Entity
@Table(name="livro")
public class EntityLivro extends EntityItemAcervo implements IFAcervo{
	
	
	private int isbn;
	
	@OneToMany(
	        targetEntity=EntityAutor.class,
	        cascade=CascadeType.MERGE,
	        fetch=FetchType.EAGER
	)
	@Fetch(FetchMode.SELECT)
    @JoinTable(
          name="autor_has_livro",
          joinColumns=@JoinColumn(name="livro_id"),
          inverseJoinColumns=@JoinColumn(name="autor_id")
    )
	private List<EntityAutor> autores;
	
	@Transient
	private String id_autor;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "editora_id",nullable=false)
	private EntityEditora editora;
	
	@Transient
	private String id_editora;
	
	@Transient
	private String string_data;
	
	@Column(name="num_pag")
	private int numero_paginas;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "tema_id",nullable=false)
	private EntityTema tema;
	
	@Transient
	private String id_tema;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem par�metros definidos)
	 */
	public EntityLivro() {		
	}
	
	/**
	 * Método construtor da classe(utilizado para criar um objeto com par�metros definidos)
	 * @param isbn isbn do livro
	 * @param titulo titulo do livro
	 * @param autores array de objetos do tipo Autor com os autores do livro
	 * @param editora objeto do tipo Editora referente ao livro
	 * @param ano_publicacao ano de publicacao do livro
	 * @param edicao edicao do livro
	 * @param numero_paginas numero de paginas do livro
	 * @param area objeto do tipo AreaConhecimento referente ao livro
	 */
	public EntityLivro(Livro livro) {
		setIsbn(livro.getIsbn());
		setTitulo(livro.getTitulo());
		ArrayList<EntityAutor> autores = new ArrayList<EntityAutor>();
		for(Autor a : livro.getAutores()){
			autores.add(new EntityAutor(a));
		}
		setAutores(autores);
		setEditora(new EntityEditora(livro.getEditora()));
		setData(livro.getAnoPublicacao());
		setEdicao(livro.getEdicao());
		setNumero_paginas(livro.getNumero_paginas());
		setTema(new EntityTema(livro.getTema()));
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public List<EntityAutor> getAutores() {
		return autores;
	}

	public void setAutores(List<EntityAutor> autores) {
		this.autores = autores;
	}

	public EntityEditora getEditora() {
		return editora;
	}

	public void setEditora(EntityEditora editora) {
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

	public EntityTema getTema() {
		return tema;
	}

	public void setTema(EntityTema tema) {
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
	
	
	public String getId_autor() {
		return id_autor;
	}

	public void setId_autor(String id_autor) {
		this.id_autor = id_autor;
	}

	public boolean validaItem() {
		return true;
	}
	
}
