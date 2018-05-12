package br.uepb.model.jpaEntity.acervo;

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
import br.uepb.model.Cidade;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_tcc;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.EntityOrientador;
/**
 * Classe utilizada para fazer o mapeamento da classe Tcc para a base de dados
 * A classe contém os respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */

@Entity
@Table(name="tcc")
public class EntityTcc extends EntityItemAcervo implements IFAcervo{ 
	
	@Transient
	@NotBlank(message="Data obrigatória")
	private String data_string;
	
	@Transient
	@NotBlank(message="Autor obrigatório")
	private String id_autor;
	
	@Transient
	@NotBlank(message="Cidade obrigatório")
	private String id_cidade;

	@Transient
	@NotBlank(message="Orientador obrigatório")
	private String id_orientador;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "autor_id",nullable=false)
	private EntityAutor autor;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "orientador_id",nullable=false)
	private EntityOrientador orientador;

	@NotNull(message=" Tipo não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	private Tipo_tcc tipo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "cidade_id",nullable=false)	
	private Cidade cidade;
	
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem par�metros definidos)
	 */
	public EntityTcc() {		
	}
	/**
	 * Método construtor da classe (utilizado para criar um objeto com par�metros definidos)
	 * @param id id do tcc
	 * @param titulo titulo do tcc
	 * @param autor objeto do tipo Autor referente ao tcc
	 * @param orientador objeto do tipo Orientador referente ao tcc
	 * @param tipo Enum que define o tipo do tcc
	 * @param ano_defesa ano da defesa do tcc
	 * @param cidade cidade da defesa do tcc
	 */
	public EntityTcc(Tcc tcc) {
		setId(tcc.getId());
		setTitulo(tcc.getTitulo());
		setAutor(new EntityAutor(tcc.getAutor()));
		setOrientador(new EntityOrientador(tcc.getOrientador()));
		setTipo(tcc.getTipo());
		setAno_defesa(tcc.getAno_defesa());
		setCidade(tcc.getCidade());
	}

	public EntityAutor getAutor() {
		return autor;
	}
	public void setAutor(EntityAutor autor) {
		this.autor = autor;
	}
	public EntityOrientador getOrientador() {
		return orientador;
	}
	public void setOrientador(EntityOrientador orientador) {
		this.orientador = orientador;
	}
	public Tipo_tcc getTipo() {
		return tipo;
	}
	public void setTipo(Tipo_tcc tipo) {
		this.tipo = tipo;
	}
	public Date getAno_defesa() {
		return getData();
	}
	public void setAno_defesa(Date ano_defesa) {
		setData(ano_defesa);
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public boolean validaItem() {
		return true;
	}
	public String getData_string() {
		return data_string;
	}
	public void setData_string(String data_string) {
		this.data_string = data_string;
	}
	public String getId_autor() {
		return id_autor;
	}
	public void setId_autor(String id_autor) {
		this.id_autor = id_autor;
	}
	public String getId_cidade() {
		return id_cidade;
	}
	public void setId_cidade(String id_cidade) {
		this.id_cidade = id_cidade;
	}
	public String getId_orientador() {
		return id_orientador;
	}
	public void setId_orientador(String id_orientador) {
		this.id_orientador = id_orientador;
	}
}
