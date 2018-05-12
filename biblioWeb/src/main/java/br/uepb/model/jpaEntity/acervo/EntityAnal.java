package br.uepb.model.jpaEntity.acervo;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.acervo.Anal;
import br.uepb.model.enums.Tipo_anal;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.EntityCidade;

/**
 * Essa classe utilizada para fazer o mapeamento da classe Anal para a base de dados.
 * A classe contémos respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */

@Entity
@Table(name="anal")
public class EntityAnal extends EntityItemAcervo implements IFAcervo{

	@Enumerated(EnumType.STRING)
	private Tipo_anal tipo;
	
	@OneToMany(
	        targetEntity=EntityAutor.class,
	        cascade=CascadeType.MERGE,
	        fetch=FetchType.EAGER
	)
	@Fetch(FetchMode.SELECT)
    @JoinTable(
          name="autor_has_anal",
          joinColumns=@JoinColumn(name="anal_id"),
          inverseJoinColumns=@JoinColumn(name="autor_id")
    )
	private List<EntityAutor> autores;
	
	
	
	@Column(name="congresso")
	private String nome_congresso;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name = "cidade_id",nullable=false)
	private EntityCidade local;
	
	/**
	 * M�todo construtor da classe Anal
	 * Construtor vazio (utilizado para criar um objeto do tipo Anal sem par�metros definidos)
	 */
	public EntityAnal() {
		
	}
	
	/**
	 * Método construtor da classe Anal (utilizado para criar um objeto do tipo Anal com par�metros definidos)
	 * @param id id do anal
	 * @param tipo Enum do tipo do anal
	 * @param titulo titulo do anal
	 * @param autor  objeto do tipo Autor referente ao anal
	 * @param nome_congresso  nome do congresso onde o anal foi apresentado
	 * @param anoPublicacao  ano de publicacao do anal
	 * @param local objeto do tipo Cidade referente ao anal
	 */
	public EntityAnal(Anal anal){
		if(anal!=null){
			setId(anal.getId());
			setTipo(anal.getTipo());
			setTitulo(anal.getTitulo());
			
			ArrayList<EntityAutor> autores = new ArrayList<EntityAutor>();
			for(Autor a : anal.getAutores()){
				autores.add(new EntityAutor(a));
			}
			setAutores(autores);
			setNome_congresso(anal.getNome_congresso());
			setAnoPublicacao(anal.getAnoPublicacao());
			setLocal(new EntityCidade(anal.getLocal()));
		}
	}
	public Tipo_anal getTipo() {
		return tipo;
	}
	public void setTipo(Tipo_anal tipo) {
		this.tipo = tipo;
	}
	public List<EntityAutor> getAutores() {
		return this.autores;
	}
	public void setAutores(ArrayList<EntityAutor> autores) {
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
	public EntityCidade getLocal() {
		return local;
	}
	public void setLocal(EntityCidade local) {
		this.local = local;
	}
	

	public boolean validaItem() {
		return true;
	}
	
}
