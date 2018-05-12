package br.uepb.model.acervo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.Orientador;
import br.uepb.model.enums.Tipo_tcc;
/**
 * Essa classe utilizada como modelo para um objeto do tipo Tcc.
 * A classe contém os respectivos getters and setters de seus atributos.
 * A classe Tcc extends da classe ItemAcervo e implementa a interface IFAcervo
 * @author EquipeACL
 */
public class Tcc extends ItemAcervo implements IFAcervo{ 
	
	private Autor autor;
	
	private Orientador orientador;
	
	@NotNull(message=" Tipo não pode ser nulo!")
	private Tipo_tcc tipo;
	
	private Cidade cidade;
	
	@NotBlank(message="Data obrigatória")
	private String data_string;
	
	@NotBlank(message="Autor obrigatório")
	private String id_autor;
	
	@NotBlank(message="Cidade obrigatório")
	private String id_cidade;
	
	@NotBlank(message="Orientador obrigatório")
	private String id_orientador;
	/**
	 * Método construtor da classe Tcc
	 * Construtor vazio (utilizado para criar um objeto do tipo Tcc sem parametros definidos)
	 */
	public Tcc() {		
	}
	/**
	 * Método construtor da classe Tcc (utilizado para criar um objeto do tipo Tcc com parametros definidos)
	 * @param id id do tcc
	 * @param titulo titulo do tcc
	 * @param autor objeto do tipo Autor referente ao tcc
	 * @param orientador objeto do tipo Orientador referente ao tcc
	 * @param tipo Enum que define o tipo do tcc
	 * @param ano_defesa ano da defesa do tcc
	 * @param cidade cidade da defesa do tcc
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
	
	
	public String getData_string() {
		return data_string;
	}
	public void setData_string(String data_string) {
		this.data_string = data_string;
	}
	public String getId_autor() {
		return id_autor;
	}
	public void setId_autor(String id_autor) {
		this.id_autor = id_autor;
	}
	public String getId_cidade() {
		return id_cidade;
	}
	public void setId_cidade(String id_cidade) {
		this.id_cidade = id_cidade;
	}
	public String getId_orientador() {
		return id_orientador;
	}
	public void setId_orientador(String id_orientador) {
		this.id_orientador = id_orientador;
	}
	public boolean validaItem() {
		return true;
	}
}
