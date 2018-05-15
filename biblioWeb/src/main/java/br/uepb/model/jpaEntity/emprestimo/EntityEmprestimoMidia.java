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
@Table(name="emp_midia")
public class EntityEmprestimoMidia extends EntityEmprestimo{
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemMidia> emprestimos;
	
	public EntityEmprestimoMidia(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemMidia>();		
	}
	
	public EntityEmprestimoMidia(ArrayList<EntityItemMidia> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemMidia item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemMidia item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemMidia buscaItem(String titulo) throws Exception{
		
		for(EntityItemMidia item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemMidia> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemMidia> emprestimos) {
		this.emprestimos = emprestimos;
	}

}
