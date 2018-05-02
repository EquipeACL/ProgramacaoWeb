package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.Orientador;
import br.uepb.model.enums.Tipo_tcc;
/**
 * Essa classe � utilizada como modelo para um objeto do tipo Tcc.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Tcc implementa a interface Acervo
 * @author EquipeACL
 */
public class Tcc extends ItemAcervo implements IFAcervo{ 
	@NotNull(message = " Autor não pode ser nulo!")
	private Autor autor;
	
	@NotNull(message = " Orientador não pode ser nulo!")
	private Orientador orientador;
	
	@NotNull(message = " Tipo não pode ser nulo!")
	private Tipo_tcc tipo;
	
	@NotNull(message = " Cidade não pode ser nula!")
	private Cidade cidade;
	
	/**
	 * M�todo construtor da classe Tcc
	 * Construtor vazio (utilizado para criar um objeto do tipo Tcc sem par�metros definidos)
	 */
	public Tcc() {		
	}
	/**
	 * M�todo construtor da classe Tcc (utilizado para criar um objeto do tipo Tcc com par�metros definidos)
	 * @param id, id do tcc
	 * @param titulo, titulo do tcc
	 * @param autor, objeto do tipo Autor referente ao tcc
	 * @param orientador, objeto do tipo Orientador referente ao tcc
	 * @param tipo, Enum que define o tipo do tcc
	 * @param ano_defesa, ano da defesa do tcc
	 * @param cidade, cidade da defesa do tcc
	 */
	public Tcc(int id, String titulo, Autor autor, Orientador orientador, Tipo_tcc tipo, Date ano_defesa, Cidade cidade) {
		setId(id);
		setTitulo(titulo);
		setAutor(autor);
		setOrientador(orientador);
		setTipo(tipo);
		setAno_defesa(ano_defesa);
		setCidade(cidade);
	}

	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	public Orientador getOrientador() {
		return orientador;
	}
	public void setOrientador(Orientador orientador) {
		this.orientador = orientador;
	}
	public Tipo_tcc getTipo() {
		return tipo;
	}
	public void setTipo(Tipo_tcc tipo) {
		this.tipo = tipo;
	}
	public Date getAno_defesa() {
		return getData();
	}
	public void setAno_defesa(Date ano_defesa) {
		setData(ano_defesa);
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public boolean validaItem() {
		return true;
	}
	
}
