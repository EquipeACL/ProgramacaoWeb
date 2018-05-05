package br.uepb.model.enums;

/**
 * Essa classe � respons�vel por criar os Enum utilizados como atributo de um objeto do tipo Curso
 * @author EquipeACL
 *
 */
public enum Tipo_curso {
	
	GRADUACAO("GRADUACAO"), POS_GRADUCAO("POS_GRADUCAO");
	private String descricao;
	private Tipo_curso(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
