package br.uepb.interfaces;

import java.sql.Date;

public interface ItemEmprestimoIF {
	
	public int getId();
	
	public void setId(int id);
	
	public Date getDataDoEmprestimo();
	
	public void setDataDoEmprestimo(Date dataDoEmprestimo);
	
	public Date getDataDeEntrega();
	
	public void setDataDeEntrega(Date dataDeEntrega);
	
	public IFAcervo getItem();

	public void setItem(IFAcervo item);

	public EmprestimoIF getEmprestimo();

	public void setEmprestimo(EmprestimoIF emprestimo);

}
