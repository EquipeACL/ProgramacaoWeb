package br.edu.ufab.model.enums;

public enum TipoDeCurso {

	GRADUAÇÃO("Graduação"),
	ESPECIALIZAÇÃO("Especialização"),
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
