package br.uepb.model;

import br.uepb.interfaces.IFDependencia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Tema.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * @author EquipeACL
 */
public class Tema implements IFDependencia{
	private int id;
	private String nome;
	private AreaConhecimento area;
	
	/**
	 * M�todo construtor da classe Tema
	 * Construtor vazio (utilizado para criar um objeto do tipo Tema sem par�metros definidos)
	 */
	public Tema(){
		
	}
	
	/**
	 * M�todo construtor da classe Tema (utilizado para criar um objeto do tipo Tema com par�metros definidos)
	 * @param id, id do tema
	 * @param nome, nome do tema
	 * @param area, objeto do tipo Area, referente ao tema
	 */
	public Tema(int id, String nome,AreaConhecimento area) {
		setId(id);
		setNome(nome);
		setArea(area);
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
	
	public AreaConhecimento getArea() {
		return area;
	}

	public void setArea(AreaConhecimento area) {
		this.area = area;
	}

	public boolean validaDependencia() {
		return true;
	}
}
