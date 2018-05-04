package br.uepb.biblio.service.exception;

public class ItemDuplicadoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ItemDuplicadoException(String message) {
		super(message);
	}

}
