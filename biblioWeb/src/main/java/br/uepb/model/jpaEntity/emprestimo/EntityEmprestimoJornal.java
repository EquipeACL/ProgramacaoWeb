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
@Table(name="emp_jornal")
public class EntityEmprestimoJornal extends EntityEmprestimo{
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemJornal> emprestimos;
	
	public EntityEmprestimoJornal(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemJornal>();		
	}
	
	public EntityEmprestimoJornal(ArrayList<EntityItemJornal> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemJornal item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemJornal item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemJornal buscaItem(String titulo) throws Exception{
		
		for(EntityItemJornal item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemJornal> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemJornal> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
