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
@Table(name="emp_livro")
public class EntityEmprestimoLivro extends EntityEmprestimo{
		
	@OneToMany(cascade = CascadeType.ALL,mappedBy="emprestimo",fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<EntityItemLivro> emprestimos;
	
	public EntityEmprestimoLivro(){
		this.aluno = new EntityAluno();
		this.emprestimos = new ArrayList<EntityItemLivro>();		
	}
	
	public EntityEmprestimoLivro(ArrayList<EntityItemLivro> emprestimos) {
		super();
		setAluno(aluno);
		setEmprestimos(emprestimos);
	}
	
	public boolean adicionarItem(EntityItemLivro item){
		emprestimos.add(item);
		return true;
	}
	
	public boolean removerItem(EntityItemLivro item){
		emprestimos.remove(item);
		return true;
	}
	
	public EntityItemLivro buscaItem(String titulo) throws Exception{
		
		for(EntityItemLivro item : emprestimos){
			if(item.getItem().getTitulo().equals(titulo)){
				return item;
			}
		}
		throw new ItemNaoEncontradoException("Item não encontrado");
	}
	public List<EntityItemLivro> getEmprestimos() {
		return emprestimos;
	}
	public void setEmprestimos(List<EntityItemLivro> emprestimos) {
		this.emprestimos = emprestimos;
	}
}
