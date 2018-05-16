package br.uepb.model;

import java.util.ArrayList;

import br.uepb.interfaces.EmprestimoIF;
import br.uepb.interfaces.ItemEmprestimoIF;
import br.uepb.model.usuarios.Aluno;

public class Emprestimo {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result
				+ ((emprestimos == null) ? 0 : emprestimos.hashCode());
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
		Emprestimo other = (Emprestimo) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (emprestimos == null) {
			if (other.emprestimos != null)
				return false;
		} else if (!emprestimos.equals(other.emprestimos))
			return false;
		return true;
	}
	

}
