package br.uepb.model.jpaEntity.emprestimo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.uepb.biblio.service.exception.ItemNaoEncontradoException;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
@Entity
@Table(name="emp_anal")
public class EntityEmprestimoAnal extends EntityEmprestimo{
	
	@OneToMany(targetEntity=EntityItemAnal.class,cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemAnal> emprestimos;
	

	
	public EntityEmprestimoAnal(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemAnal>();		
	}
	
	public EntityEmprestimoAnal(ArrayList<EntityItemAnal> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemAnal item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemAnal item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemEmprestimo buscaItem(String titulo) throws Exception{
		
		for(EntityItemAnal item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemAnal> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemAnal> emprestimos) {
		this.emprestimos = emprestimos;
	}
}
