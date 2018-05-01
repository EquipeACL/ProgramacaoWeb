package br.uepb.model.usuarios;
/**
 * Essa classe � utilizada como modelo para um objeto do tipo Funcion�rio;
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Aluno extende a classe Usu�rio, que cont�m os atributos e m�todos comuns a todos os usu�rios do sistema.
 * @author EquipeACL
 */
public class Funcionario extends Usuario {
	
	
	public Funcionario() {
		
	}	
	/**
	 * M�todo construtor da classe Funcion�rio
	 * @param cpf, n�mero do CPF do Funcion�rio
	 * @param nomeCompleto, nome completo do Funcion�rio
	 * @param rg, n�mero do RG do Funcion�rio
	 * @param naturalidade, cidade natal do Funcion�rio
	 * @param endereco, endere�o completo do Funcion�rio
	 * @param telefone, numero de telefone do Funcion�rio
	 * @param email, endere�o de email do Funcion�rio
	 * @param senhaAcesso, senha de acesso ao sistema do Funcion�rio
	 * @param nomeUsuario, nome de usuario no sistema do Funcion�rio
	 */
	public Funcionario(int cpf, String nomeCompleto, int rg, String naturalidade, String endereco, int telefone,
			String email, String senhaAcesso, String nomeUsuario) {
		super(cpf, nomeCompleto, rg, naturalidade, endereco, telefone, email, senhaAcesso);
		setNomeUsuario(nomeUsuario);
	}
}
