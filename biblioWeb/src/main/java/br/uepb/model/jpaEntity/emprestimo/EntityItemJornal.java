package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.model.jpaEntity.acervo.EntityJornal;

/**
 * Essa classe é utilizada como modelo para um objeto do tipo EntityItemJornal.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="item_jornal")
public class EntityItemJornal extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jornal")
	private EntityJornal item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoJornal emprestimo;

	public EntityJornal getItem() {
		return item;
	}

	public void setItem(EntityJornal item) {
		this.item = item;
	}

	public EntityEmprestimoJornal getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoJornal emprestimo) {
		this.emprestimo = emprestimo;
	}

	
}
