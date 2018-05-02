package br.uepb.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFDependencia;
import br.uepb.model.acervo.Livro;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Editora.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
@Entity
@Table(name = "editora")
public class Editora implements IFDependencia{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = " Nome é obrigatório")
	private String nome;
	
	
	//private List<Livro> livros;
	
	/**
	 * M�todo construtor da classe Editora
	 * Construtor vazio (utilizado para criar um objeto do tipo Editora sem par�metros definidos)
	 */
	public Editora() {

	}
	
	public Editora(String nome){
		setNome(nome);
	}
	
	/**
	 * M�todo construtor da classe Editora (utilizado para criar um objeto do tipo Editora com par�metros definidos)
	 * @param id, id da editora
	 * @param nome, nome da editora
	 */
	public Editora(int id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean validaDependencia() {
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editora other = (Editora) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
