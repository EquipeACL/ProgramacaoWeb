package br.uepb.model.acervo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@NotBlank(message=" Autor obrigatório")
	private String id_autor;
	
	@Transient
	@NotBlank(message=" Data é obrigatório")
	private String data_string;
	
	@Transient
	@NotBlank(message=" Cidade obrigatório")
	private String id_cidade;

	@OneToMany(
	        targetEntity=Autor.class,
	        cascade=CascadeType.MERGE,
	        fetch=FetchType.EAGER
	)
	@Fetch(FetchMode.SELECT)
    @JoinTable(
          name="autor_has_anal",
          joinColumns=@JoinColumn(name="anal_id"),
          inverseJoinColumns=@JoinColumn(name="autor_id")
    )
	private List<Autor> autores;
	
	
	@NotBlank(message = " Nome do Congresso é Obrigatório")
	@Column(name="congresso")
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
	public Anal(int id,Tipo_anal tipo, String titulo, ArrayList<Autor> autores, String nome_congresso, Date anoPublicacao, Cidade local){
		setId(id);
		setTipo(tipo);
		setTitulo(titulo);
		setAutor(autores);
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
	public List<Autor> getAutor() {
		return this.autores;
	}
	public void setAutor(ArrayList<Autor> autores) {
		this.autores = autores;
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

	public String getData_string() {
		return data_string;
	}

	public void setData_string(String data_string) {
		this.data_string = data_string;
	}

	public String getId_cidade() {
		return id_cidade;
	}

	public void setId_cidade(String id_cidade) {
		this.id_cidade = id_cidade;
	}

	public boolean validaItem() {
		return true;
	}
	
}
