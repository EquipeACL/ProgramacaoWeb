package br.uepb.biblio.service.exception;

public class LoginDuplicadoException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	public LoginDuplicadoException (String message) {
		super(message);
	}
	
}
