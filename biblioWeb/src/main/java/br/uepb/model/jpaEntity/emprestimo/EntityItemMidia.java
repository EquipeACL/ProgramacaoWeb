package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

/**
 * Essa classe é utilizada como modelo para um objeto do tipo EntityItemMidia.
 * A classe contém os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name="item_midia")
public class EntityItemMidia extends EntityItemEmprestimo{
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_midia")
	private EntityMidiasEletronicas item;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_emprestimo")
    private EntityEmprestimoMidia emprestimo;

	public EntityMidiasEletronicas getItem() {
		return item;
	}

	public void setItem(EntityMidiasEletronicas item) {
		this.item = item;
	}

	public EntityEmprestimoMidia getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EntityEmprestimoMidia emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	

}
