package br.uepb.model.jpaEntity.emprestimo;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.model.jpaEntity.acervo.EntityLivro;

@Entity
@Table(name="item_livro")
public class EntityItemLivro extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tcc")
	private EntityLivro item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoLivro emprestimo;

	public EntityLivro getItem() {
		return item;
	}

	public void setItem(EntityLivro item) {
		this.item = item;
	}

	public EntityEmprestimoLivro getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoLivro emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	
}
