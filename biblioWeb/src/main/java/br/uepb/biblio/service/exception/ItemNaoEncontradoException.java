package br.uepb.biblio.service.exception;

public class ItemNaoEncontradoException extends Exception {
private static final long serialVersionUID = 1L;
	
	public ItemNaoEncontradoException(String message) {
		super(message);
	}
}
