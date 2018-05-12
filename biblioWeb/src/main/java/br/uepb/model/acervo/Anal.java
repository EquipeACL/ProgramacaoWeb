package br.uepb.model.acervo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.enums.Tipo_anal;

/**
 * Classe utilizada como modelo para um objeto do tipo Anal.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe Anal extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */


public class Anal extends ItemAcervo implements IFAcervo{
	
	@NotNull(message=" Tipo é obrigatório")
	private Tipo_anal tipo;	
	
	//@NotNull(message=" Autor é obrigatório")
	private List<Autor> autores;	
	
	@NotBlank(message = " Nome do Congresso é Obrigatório")
	private String nome_congresso;	
	
	@NotBlank(message = " Pelo menos um autor.")
	private String id_autor;	
	
	@NotNull(message=" Cidade é obrigatório")
	private Cidade local;	
	
	/**
	 * Método construtor da classe Anal
	 * Construtor vazio (utilizado para criar um objeto do tipo Anal sem par�metros definidos)
	 */
	public Anal() {
		
	}
	
	/**
	 * Método construtor da classe Anal (utilizado para criar um objeto do tipo Anal com par�metros definidos)
	 * @param id id do anal
	 * @param tipo Enum do tipo do anal
	 * @param titulo titulo do anal
	 * @param autor objeto do tipo Autor referente ao anal
	 * @param nome_congresso nome do congresso onde o anal foi apresentado
	 * @param anoPublicacao ano de publicacao do anal
	 * @param local objeto do tipo Cidade referente ao anal
	 */
	public Anal(int id,Tipo_anal tipo, String titulo, List<Autor> autores, String nome_congresso, Date anoPublicacao, Cidade local){
		setId(id);
		setTipo(tipo);
		setTitulo(titulo);
		setAutores(autores);
		setNome_congresso(nome_congresso);
		setAnoPublicacao(anoPublicacao);
		setLocal(local);
	}
	public Tipo_anal getTipo() {
		return tipo;
	}
	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public void setTipo(Tipo_anal tipo) {
		this.tipo = tipo;
	}
	public String getNome_congresso() {
		return nome_congresso;
	}
	public void setNome_congresso(String nome_congresso) {
		this.nome_congresso = nome_congresso;
	}
	public Date getAnoPublicacao() {
		return getData();
	}
	public void setAnoPublicacao(Date anoPublicacao) {
		setData(anoPublicacao);
	}
	public Cidade getLocal() {
		return local;
	}
	public void setLocal(Cidade local) {
		this.local = local;
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
