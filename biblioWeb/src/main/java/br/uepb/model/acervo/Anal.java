package br.uepb.model.acervo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.enums.Tipo_anal;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Anal.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Anal implementa a interface Acervo
 * @author EquipeACL
 */

@Entity
@Table(name="anal")
public class Anal extends ItemAcervo implements IFAcervo{

	@NotNull(message=" Tipo não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	private Tipo_anal tipo;
	
	@Transient
	@NotBlank(message="Autor obrigatório")
	private String id_autor;
	
	@Transient
	@NotBlank(message="Cidade obrigatório")
	private String id_cidade;

	//ManyToMany
	private Autor autor;
	
	@NotBlank(message = " Nome do Congresso é Obrigatório")
	private String nome_congresso;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "cidade_id",nullable=false)
	private Cidade local;
	
	/**
	 * M�todo construtor da classe Anal
	 * Construtor vazio (utilizado para criar um objeto do tipo Anal sem par�metros definidos)
	 */
	public Anal() {
		
	}
	
	/**
	 * M�todo construtor da classe Anal (utilizado para criar um objeto do tipo Anal com par�metros definidos)
	 * @param id, id do anal
	 * @param tipo, Enum do tipo do anal
	 * @param titulo, titulo do anal
	 * @param autor, objeto do tipo Autor referente ao anal
	 * @param nome_congresso, nome do congresso onde o anal foi apresentado
	 * @param anoPublicacao, ano de publicacao do anal
	 * @param local, objeto do tipo Cidade referente ao anal
	 */
	public Anal(int id,Tipo_anal tipo, String titulo, Autor autor, String nome_congresso, Date anoPublicacao, Cidade local){
		setId(id);
		setTipo(tipo);
		setTitulo(titulo);
		setAutor(autor);
		setNome_congresso(nome_congresso);
		setAnoPublicacao(anoPublicacao);
		setLocal(local);
	}
	public Tipo_anal getTipo() {
		return tipo;
	}
	public void setTipo(Tipo_anal tipo) {
		this.tipo = tipo;
	}
	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
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
	public boolean validaItem() {
		return true;
	}
	
}
