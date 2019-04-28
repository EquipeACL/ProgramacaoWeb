package br.edu.ufab.model.enums;

public enum TipoDeMidia {

	CD("CD"), DVD("DVD");
	
	private String descricao;
	
	TipoDeMidia(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
