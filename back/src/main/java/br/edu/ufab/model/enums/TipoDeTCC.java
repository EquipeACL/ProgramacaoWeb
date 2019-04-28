package br.edu.ufab.model.enums;
/**
 * Enum utilizado para classificar os possiveis tipos de trabalhos de conclusão de curso.
 * @author EquipeACL
 *
 */
public enum TipoDeTCC {

	MONOGRAFIA("Monografia"),
	TESE("Tese"),
	DISSERTACAO("Dissertação");
	private String descricao;
	
	TipoDeTCC(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
