package br.edu.ufab.model.enums;

public enum TipoDeAnais {

	ARTIGO("Artigo"), POSTER("Poster"), RESUMO("Resumo");

	private String descricao;
	
	TipoDeAnais(String descricao){
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
}
