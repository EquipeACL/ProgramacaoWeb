package br.uepb.model.jpaEntity.acervo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_tcc;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.EntityCidade;
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
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "autor_id",nullable=false)
	private EntityAutor autor;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "orientador_id",nullable=false)
	private EntityOrientador orientador;

	
	@Enumerated(EnumType.STRING)
	private Tipo_tcc tipo;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "cidade_id",nullable=false)	
	private EntityCidade cidade;
	
	
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
		if(tcc!=null){
			setId(tcc.getId());
			setTitulo(tcc.getTitulo());
			setAutor(new EntityAutor(tcc.getAutor()));
			setOrientador(new EntityOrientador(tcc.getOrientador()));
			setTipo(tcc.getTipo());
			setAno_defesa(tcc.getAno_defesa());
			setCidade(new EntityCidade(tcc.getCidade()));
		}
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
	public EntityCidade getCidade() {
		return cidade;
	}
	public void setCidade(EntityCidade cidade) {
		this.cidade = cidade;
	}
	public boolean validaItem() {
		return true;
	}
	
}
