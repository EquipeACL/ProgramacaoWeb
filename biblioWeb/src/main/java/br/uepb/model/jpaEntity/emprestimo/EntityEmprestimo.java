package br.uepb.model.jpaEntity.emprestimo;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.uepb.model.jpaEntity.usuarios.EntityAluno;
@MappedSuperclass
public abstract class EntityEmprestimo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "id_aluno",nullable=false)
	protected EntityAluno aluno;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntityAluno getAluno() {
		return aluno;
	}
	public void setAluno(EntityAluno aluno) {
		this.aluno = aluno;
	}	


}
