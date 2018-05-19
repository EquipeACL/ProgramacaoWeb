package br.uepb.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.uepb.interfaces.EmprestimoIF;
import br.uepb.model.jpaEntity.acervo.EntityAnal;
import br.uepb.model.jpaEntity.acervo.EntityJornal;
import br.uepb.model.jpaEntity.acervo.EntityLivro;
import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;
import br.uepb.model.jpaEntity.acervo.EntityRevista;
import br.uepb.model.jpaEntity.acervo.EntityTcc;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Aluno;
@Entity
@Table(name="emprestimo")
public class Emprestimo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		 
	@NotNull(message=" Data de emprestimo é obrigatorio")
	@Column(name="data_emp")
	private Date dataDoEmprestimo;	
	
	@NotNull(message=" Data de entrega é obrigatorio")
	@Column(name="data_entrega")
	private Date dataDeEntrega;
	
	@NotNull(message=" Aluno é obrigatorio")
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "id_aluno",nullable=false)
	private EntityAluno aluno;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_livro")
	private EntityLivro livro;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_anal")
	private EntityAnal anal;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_revista")
	private EntityRevista revista;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_midia")
	private EntityMidiasEletronicas midia;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_tcc")
	private EntityTcc tcc;
	
	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_jornal")
	private EntityJornal jornal;
	
	public Emprestimo(){
		this.aluno = new EntityAluno();
		
	}
	
	public Emprestimo(Aluno aluno, ArrayList<ItemEmprestimo> emprestimos) {
		super();
		setAluno(aluno);
		
	}
	
	public Emprestimo(EmprestimoIF emprestimo) {
		super();
		setAluno(new Aluno(emprestimo.getAluno()));
		
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntityAluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = new EntityAluno(aluno);
	}
	
	public Date getDataDoEmprestimo() {
		return dataDoEmprestimo;
	}

	public void setDataDoEmprestimo(Date dataDoEmprestimo) {
		this.dataDoEmprestimo = dataDoEmprestimo;
	}

	public Date getDataDeEntrega() {
		return dataDeEntrega;
	}

	public void setDataDeEntrega(Date dataDeEntrega) {
		this.dataDeEntrega = dataDeEntrega;
	}

	public EntityLivro getLivro() {
		return livro;
	}

	public void setLivro(EntityLivro livro) {
		this.livro = livro;
	}

	public EntityAnal getAnal() {
		return anal;
	}

	public void setAnal(EntityAnal anal) {
		this.anal = anal;
	}

	public EntityRevista getRevista() {
		return revista;
	}

	public void setRevista(EntityRevista revista) {
		this.revista = revista;
	}

	public EntityMidiasEletronicas getMidia() {
		return midia;
	}

	public void setMidia(EntityMidiasEletronicas midia) {
		this.midia = midia;
	}

	public EntityTcc getTcc() {
		return tcc;
	}

	public void setTcc(EntityTcc tcc) {
		this.tcc = tcc;
	}

	public EntityJornal getJornal() {
		return jornal;
	}

	public void setJornal(EntityJornal jornal) {
		this.jornal = jornal;
	}
}
