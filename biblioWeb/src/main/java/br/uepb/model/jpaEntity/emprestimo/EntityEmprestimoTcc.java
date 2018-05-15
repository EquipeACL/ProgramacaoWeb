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
@Table(name="emp_tcc")
public class EntityEmprestimoTcc extends EntityEmprestimo{
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemTcc> emprestimos;
	
	public EntityEmprestimoTcc(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemTcc>();		
	}
	
	public EntityEmprestimoTcc(ArrayList<EntityItemTcc> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemTcc item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemTcc item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemTcc buscaItem(String titulo) throws Exception{
		
		for(EntityItemTcc item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemTcc> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemTcc> emprestimos) {
		this.emprestimos = emprestimos;
	}
}
