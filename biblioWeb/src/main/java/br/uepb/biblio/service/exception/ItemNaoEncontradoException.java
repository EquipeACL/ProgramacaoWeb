package br.uepb.biblio.service.exception;


/**
 * Essa é a classe responsável pelo serviço de notificação da exceção de Item Não Encontrado
 * @author EquipeACL
 *
 */
public class ItemNaoEncontradoException extends Exception {
private static final long serialVersionUID = 1L;
	
	/**
	 * Método responsável por notificar o Item Não Encontrado
	 * @param message, que é a mensagem referente ao item duplicado
	 */
	public ItemNaoEncontradoException(String message) {
		super(message);
	}
}
