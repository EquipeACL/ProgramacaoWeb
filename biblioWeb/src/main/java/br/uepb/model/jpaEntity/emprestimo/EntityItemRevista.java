package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.model.jpaEntity.acervo.EntityRevista;

@Entity
@Table(name="item_revista")
public class EntityItemRevista extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_revista")
	private EntityRevista item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoRevista emprestimo;

	public EntityRevista getItem() {
		return item;
	}

	public void setItem(EntityRevista item) {
		this.item = item;
	}

	public EntityEmprestimoRevista getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoRevista emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	

}
