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
import br.uepb.interfaces.EmprestimoIF;
import br.uepb.interfaces.ItemEmprestimoIF;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
@Entity
@Table(name="emp_revista")
public class EntityEmprestimoRevista extends EntityEmprestimo implements EmprestimoIF{
	
	@OneToMany(targetEntity=EntityItemRevista.class,cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<ItemEmprestimoIF> emprestimos;
	
	public EntityEmprestimoRevista(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<ItemEmprestimoIF>();		
	}
	
	public EntityEmprestimoRevista(ArrayList<ItemEmprestimoIF> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(ItemEmprestimoIF item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(ItemEmprestimoIF item){
		emprestimos.remove(item);
		return true;
	}
	
	public ItemEmprestimoIF buscaItem(String titulo) throws Exception{
		
		for(ItemEmprestimoIF item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
					return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<ItemEmprestimoIF> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<ItemEmprestimoIF> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
