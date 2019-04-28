package br.edu.ufab.model.enums;

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
