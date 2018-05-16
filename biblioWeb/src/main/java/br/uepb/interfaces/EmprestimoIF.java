package br.uepb.interfaces;

import java.util.List;

import br.uepb.model.jpaEntity.usuarios.EntityAluno;

public interface EmprestimoIF {
	
	public EntityAluno getAluno();
	
	public void setAluno(EntityAluno aluno);
	
	public boolean adicionarItem(ItemEmprestimoIF item);
	
	public boolean removerItem(ItemEmprestimoIF item);
	
	public ItemEmprestimoIF buscaItem(String titulo) throws Exception;
	
	public List<ItemEmprestimoIF> getEmprestimos() ;
	
	public void setEmprestimos(List<ItemEmprestimoIF> emprestimos);

}
