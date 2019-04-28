package br.edu.ufab.model.enums;

public enum AreaDeCurso {

	CIENCIAS_HUMANAS("Ciências Humanas"),
	CIENCIAS_EXATAS("Ciências Exatas"),
	CIENCIAS_BIOLOGICAS("Ciências Biologicas");
	
	private String descricao;
	
	AreaDeCurso(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
}
