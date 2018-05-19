package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.model.jpaEntity.acervo.EntityAnal;

/**
 * Essa classe é utilizada como modelo para um objeto do tipo EntityEmprestimoTcc.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="item_anal")
public class EntityItemAnal extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anal")
	private EntityAnal item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoAnal emprestimo;

	public EntityAnal getItem() {
		return item;
	}

	public void setItem(EntityAnal item) {
		this.item = item;
	}

	public EntityEmprestimoAnal getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoAnal emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	
}
