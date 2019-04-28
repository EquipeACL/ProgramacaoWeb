package br.edu.ufab.model.enums;
/**
 * Enum utilizado para classificar os possiveis tipos de curso.
 * @author EquipeACL
 *
 */
public enum TipoDeCurso {

	GRADUACAO("Graduação"),
	ESPECIALIZACAO("Especialização"),
	MESTRADO("Mestrado"),
	DOUTORADO("Doutorado");
	
	private String descricao;
	
	TipoDeCurso(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
