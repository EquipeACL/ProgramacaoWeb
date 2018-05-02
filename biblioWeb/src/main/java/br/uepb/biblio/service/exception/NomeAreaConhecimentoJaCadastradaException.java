package br.uepb.biblio.service.exception;

public class NomeAreaConhecimentoJaCadastradaException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	
	public NomeAreaConhecimentoJaCadastradaException(String message) {
		super(message);
	}
}
