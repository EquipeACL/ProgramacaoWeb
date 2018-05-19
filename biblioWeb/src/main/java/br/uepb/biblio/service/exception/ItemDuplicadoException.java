package br.uepb.biblio.service.exception;

/**
 * Essa é a classe responsável pelo serviço de notificação da exceção de Item Duplicado
 * @author EquipeACL
 *
 */
public class ItemDuplicadoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método responsável por notificar o Item Duplicado
	 * @param message, que é a mensagem referente ao item duplicado
	 */
	public ItemDuplicadoException(String message) {
		super(message);
	}

}
