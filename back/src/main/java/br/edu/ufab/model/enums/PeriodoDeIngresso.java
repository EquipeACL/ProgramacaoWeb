package br.edu.ufab.model.enums;
/**
 * Enum utilizado para classificar os possiveis periodos de ingresso de um aluno.
 * @author EquipeACL
 *
 */
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
