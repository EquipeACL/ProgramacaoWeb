package br.uepb.model.enums;
/**
 * Essa classe � respons�vel por criar os Enum utilizados como atributo de um objeto do tipo Aluno
 * @author EquipeACL
 *
 */
public enum Tipo_nivel {
	GRADUAÇÃO("Graduação"),ESPECIALIZAÇÃO("Especialização"),MESTRADO("Mestrado"),DOUTORADO("Doutorado"),POSGRADUAÇÃO("Pós-graduação"); //G-Graduacao, E-Especializa��o, M-Mestrado, D-Doutorado, P-Pos-Doutorado
	private String descricao;
	private Tipo_nivel(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}