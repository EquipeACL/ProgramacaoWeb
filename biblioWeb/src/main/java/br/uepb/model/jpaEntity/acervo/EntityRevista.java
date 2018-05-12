package br.uepb.model.jpaEntity.acervo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.acervo.Revista;
import br.uepb.model.jpaEntity.EntityEditora;
/**
 * Classe  utilizada para fazer o mapeamento da classe Revista para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */
@Entity
@Table(name="revista")
public class EntityRevista extends EntityItemAcervo implements IFAcervo{
	@Transient
	private String data_string;
	
	@Transient
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
	private EntityEditora editora;
	
	private int num_pag;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem par�metros definidos)
	 */
	public EntityRevista() {		
	}
	/**
	 * Método construtor da classe (utilizado para criar um objeto com par�metros definidos)
	 * @param titulo titulo da revista
	 * @param editora objeto do tipo Editora referente a revista
	 * @param dataDePublicacao data de publicacao da revista
	 * @param edicao edicao da revista
	 * @param numeroDePaginas numero de paginas da revista
	 */
	public EntityRevista(Revista revista) {
		setTitulo(revista.getTitulo());
		setEditora(new EntityEditora(revista.getEditora()));
		setData(revista.getDataPublicacao());
		setEdicao(revista.getEdicao());
		setNum_pag(revista.getNum_pag());
	}
	
	public EntityEditora getEditora() {
		return editora;
	}

	public void setEditora(EntityEditora editora) {
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
