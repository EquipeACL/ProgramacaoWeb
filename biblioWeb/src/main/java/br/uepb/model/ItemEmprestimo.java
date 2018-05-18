package br.uepb.model;

import java.sql.Date;

import javax.persistence.MappedSuperclass;

import br.uepb.interfaces.IFAcervo;
import br.uepb.interfaces.ItemEmprestimoIF;
@MappedSuperclass
public class ItemEmprestimo {
	
	private IFAcervo item;
	
	public ItemEmprestimo(){
		
	}
	public ItemEmprestimo(IFAcervo item, Date dataDoEmprestimo,
			Date dataDeEntrega) {
		super();
		this.item = item;
	}
	public ItemEmprestimo(ItemEmprestimoIF e) {
		setItem(e.getItem());
	}
	public IFAcervo getItem() {
		return item;
	}
	public void setItem(IFAcervo item) {
		this.item = item;
	}
	
	
	
	

}
