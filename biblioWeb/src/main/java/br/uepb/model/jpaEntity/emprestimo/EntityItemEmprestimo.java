package br.uepb.model.jpaEntity.emprestimo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Essa classe é utilizada como modelo para um objeto do tipo EntityItemEmprestimo.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@MappedSuperclass
public abstract class EntityItemEmprestimo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="data_emp")
	private Date dataDoEmprestimo;
	
	@Column(name="data_entrega")
	private Date dataDeEntrega;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataDoEmprestimo() {
		return dataDoEmprestimo;
	}
	public void setDataDoEmprestimo(Date dataDoEmprestimo) {
		this.dataDoEmprestimo = dataDoEmprestimo;
	}
	public Date getDataDeEntrega() {
		return dataDeEntrega;
	}
	public void setDataDeEntrega(Date dataDeEntrega) {
		this.dataDeEntrega = dataDeEntrega;
	}
	
	
	

}
