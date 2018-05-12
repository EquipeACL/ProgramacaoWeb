package br.uepb.model.enums;
/**
 * Essa classe � respons�vel por criar os Enum utilizados como atributo de um objeto do tipo MidiasEletronicas
 * @author EquipeACL
 *
 */
public enum Tipo_midia {
	CD("CD"), DVD("DVD");
	private String descricao;
	private Tipo_midia(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
