package br.edu.ufab.model.enums;
/**
 * Enum utilizado para classificar os possiveis tipos de Anal de congresso.
 * @author EquipeACL
 *
 */
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
