package br.edu.ufab.model.enums;
/**
 * Enum utilizado para classificar os possiveis tipos de midia eletronica.
 * @author EquipeACL
 *
 */
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
