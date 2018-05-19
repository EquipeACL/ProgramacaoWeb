package br.uepb.biblio.service.exception;

/**
 * Essa é a classe responsável pelo serviço de notificação da exceção de Senha Obrigatoria
 * @author EquipeACL
 *
 */
public class SenhaObrigatoriaUsuarioException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Método responsável por notificar a Senha Obrigatoria
	 * @param message, que é a mensagem referente a Senha Obrigatoria
	 */
	public SenhaObrigatoriaUsuarioException(String message) {
		super(message);
	}

}
