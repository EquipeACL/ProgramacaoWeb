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
@Table(name="emp_revista")
public class EntityEmprestimoRevista extends EntityEmprestimo{
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemRevista> emprestimos;
	
	public EntityEmprestimoRevista(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemRevista>();		
	}
	
	public EntityEmprestimoRevista(ArrayList<EntityItemRevista> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemRevista item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemRevista item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemRevista buscaItem(String titulo) throws Exception{
		
		for(EntityItemRevista item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemRevista> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemRevista> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
