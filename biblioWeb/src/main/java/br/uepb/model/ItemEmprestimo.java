package br.uepb.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import br.uepb.interfaces.IFAcervo;
import br.uepb.interfaces.ItemEmprestimoIF;
@MappedSuperclass
public class ItemEmprestimo {
	
	private IFAcervo item;
	
	@Column(name="data_emp")
	private Date dataDoEmprestimo;
	
	@Column(name="data_entrega")
	private Date dataDeEntrega;
	
	public ItemEmprestimo(){
		
	}
	public ItemEmprestimo(IFAcervo item, Date dataDoEmprestimo,
			Date dataDeEntrega) {
		super();
		this.item = item;
		this.dataDoEmprestimo = dataDoEmprestimo;
		this.dataDeEntrega = dataDeEntrega;
	}
	public ItemEmprestimo(ItemEmprestimoIF e) {
		setItem(e.getItem());
		setDataDoEmprestimo(e.getDataDoEmprestimo());
		setDataDeEntrega(e.getDataDeEntrega());
	}
	public IFAcervo getItem() {
		return item;
	}
	public void setItem(IFAcervo item) {
		this.item = item;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDeEntrega == null) ? 0 : dataDeEntrega.hashCode());
		result = prime
				* result
				+ ((dataDoEmprestimo == null) ? 0 : dataDoEmprestimo.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemEmprestimo other = (ItemEmprestimo) obj;
		if (dataDeEntrega == null) {
			if (other.dataDeEntrega != null)
				return false;
		} else if (!dataDeEntrega.equals(other.dataDeEntrega))
			return false;
		if (dataDoEmprestimo == null) {
			if (other.dataDoEmprestimo != null)
				return false;
		} else if (!dataDoEmprestimo.equals(other.dataDoEmprestimo))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
	

}
