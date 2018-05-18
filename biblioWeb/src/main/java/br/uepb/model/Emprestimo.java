package br.uepb.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import br.uepb.interfaces.EmprestimoIF;
import br.uepb.interfaces.ItemEmprestimoIF;
import br.uepb.model.usuarios.Aluno;

public class Emprestimo {
	 
	@NotNull(message=" Data de emprestimo é obrigatorio")
	private Date dataDoEmprestimo;	
	
	@NotNull(message=" Data de entrega é obrigatorio")
	private Date dataDeEntrega;
	
	@NotNull(message=" Aluno é obrigatorio")
	private Aluno aluno;
	
	private ArrayList<ItemEmprestimo> emprestimos;
	
	public Emprestimo(){
		this.aluno = new Aluno();
		this.emprestimos = new ArrayList<ItemEmprestimo>();		
	}
	
	public Emprestimo(Aluno aluno, ArrayList<ItemEmprestimo> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public Emprestimo(EmprestimoIF emprestimo) {
		super();
		setAluno(new Aluno(emprestimo.getAluno()));
		ArrayList<ItemEmprestimo> listaTemp = new ArrayList<ItemEmprestimo>();
		for(ItemEmprestimoIF e : emprestimo.getEmprestimos()){
			listaTemp.add(new ItemEmprestimo(e));
		}
		setEmprestimos(listaTemp);
	}
	
	public boolean adicionarItem(ItemEmprestimo item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(ItemEmprestimo item){
		emprestimos.remove(item);
		return true;
	}
	
	public ItemEmprestimo buscaItem(String titulo){
		ItemEmprestimo itemRetorno = new ItemEmprestimo();
		for(ItemEmprestimo item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				itemRetorno = item;
			}
		}
		return itemRetorno.getItem() != null?itemRetorno:null;
	}
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public ArrayList<ItemEmprestimo> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(ArrayList<ItemEmprestimo> emprestimos) {
		this.emprestimos = emprestimos;
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
