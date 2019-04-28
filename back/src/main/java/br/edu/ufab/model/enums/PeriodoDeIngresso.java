package br.edu.ufab.model.enums;

public enum PeriodoDeIngresso {

	PRIMEIRO("Primeiro"), SEGUNDO("Segundo");
	
	private String descricao;
	
	PeriodoDeIngresso(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
