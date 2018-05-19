package br.uepb.biblio.service.exception;

/**
 * Essa é a classe responsável pelo serviço de notificação da exceção de Item Duplicado
 * @author EquipeACL
 *
 */
public class LoginDuplicadoException extends RuntimeException{

	/**
	 * Método responsável por notificar o Item Duplicado
	 * @param message, que é a mensagem referente ao item duplicado
	 */
	private static final long serialVersionUID = 1L;
	
	public LoginDuplicadoException (String message) {
		super(message);
	}
	
}
