package br.uepb.model.enums;
/**
 * Essa classe � respons�vel por criar os Enum utilizados como atributo de um objeto do tipo Tcc
 * @author EquipeACL
 *
 */
public enum Tipo_tcc {
	MONOGRAFIA("Monografia"), TESE("Tese"), DISSERTACAO("Dissertação");
	private String descricao;

	private Tipo_tcc(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
