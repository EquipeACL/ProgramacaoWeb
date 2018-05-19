package br.uepb.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.uepb.interfaces.EmprestimoIF;
import br.uepb.model.acervo.Anal;
import br.uepb.model.acervo.Jornal;
import br.uepb.model.acervo.Livro;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.acervo.Revista;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.usuarios.Aluno;
/*@Entity
@Table(name="emprestimo")*/
public class Emprestimo {
	@Transient
	private String id_item;
	 
	@NotNull(message=" Data de emprestimo é obrigatorio")
	private Date dataDoEmprestimo;	
	
	@NotNull(message=" Data de entrega é obrigatorio")
	private Date dataDeEntrega;
	
	@NotNull(message=" Aluno é obrigatorio")
	private Aluno aluno;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livro")
	private Livro livro;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anal")
	private Anal anal;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_revista")
	private Revista revista;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_midia")
	private MidiasEletronicas midia;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tcc")
	private Tcc tcc;
	
	private Jornal jornal;
	
	public Emprestimo(){
		this.aluno = new Aluno();
		
	}
	
	public Emprestimo(Aluno aluno, ArrayList<ItemEmprestimo> emprestimos) {
		super();
		setAluno(aluno);
		
	}
	
	public Emprestimo(EmprestimoIF emprestimo) {
		super();
		setAluno(new Aluno(emprestimo.getAluno()));
		
	}
	
		
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
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

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Anal getAnal() {
		return anal;
	}

	public void setAnal(Anal anal) {
		this.anal = anal;
	}

	public Revista getRevista() {
		return revista;
	}

	public void setRevista(Revista revista) {
		this.revista = revista;
	}

	public MidiasEletronicas getMidia() {
		return midia;
	}

	public void setMidia(MidiasEletronicas midia) {
		this.midia = midia;
	}

	public Tcc getTcc() {
		return tcc;
	}

	public void setTcc(Tcc tcc) {
		this.tcc = tcc;
	}

	public Jornal getJornal() {
		return jornal;
	}

	public void setJornal(Jornal jornal) {
		this.jornal = jornal;
	}

	public String getId_item() {
		return id_item;
	}

	public void setId_item(String id_item) {
		this.id_item = id_item;
	}
	
	

}
