package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.interfaces.EmprestimoIF;
import br.uepb.model.jpaEntity.acervo.EntityTcc;

@Entity
@Table(name="item_tcc")
public class EntityItemTcc extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tcc")
	private EntityTcc item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoTcc emprestimo;

	public EntityTcc getItem() {
		return item;
	}

	public void setItem(EntityTcc item) {
		this.item = item;
	}

	public EmprestimoIF getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoTcc emprestimo) {
		this.emprestimo = emprestimo;
	}

}
