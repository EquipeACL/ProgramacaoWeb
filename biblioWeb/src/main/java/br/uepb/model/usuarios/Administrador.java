package br.uepb.model.usuarios;

import java.util.ArrayList;

import br.uepb.dao.Item_Acervo;
import br.uepb.dao.usuarios.FuncionarioDAO;
import br.uepb.model.acervo.Acervo;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Administrador.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Administrador extende a classe Usu�rio, que cont�m os atributos e m�todos comuns a todos os usu�rios do sistema.
 * A classe Administrador implementa as interfaces que cont�m m�todos para manter o usu�rio e manter o acervo.
 * A classe Administrador cont�m um m�todo �nico, que serve para remover um aluno do sistema.
 * @author EquipeACL
 */
public class Administrador extends Usuario implements Interface_manterFuncionario,Interface_manterAcervo{
	private FuncionarioDAO funcionarioDAO;
	private String nomeUsuario;
	public Administrador() { 
		funcionarioDAO = new FuncionarioDAO();
	}
	/**
	 * M�todo construtor da classe Administrador
	 * @param nomeUsuario, que � o nome de usu�rio do Administrador no sistema 
	 */
	public Administrador(String nomeUsuario) {
		super();
		setNomeUsuario(nomeUsuario);
		this.funcionarioDAO = new FuncionarioDAO();
		
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	/**
	 * M�todo respons�vel por remover um objeto do tipo Aluno no sistema
	 * @param aluno, que � um objeto do tipo Aluno
	 * @return false, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso haja algum problema durante a remo��o do objeto passado por par�metro no banco de dados.
	 * @return true, caso haja sucesso na inser��o do objeto recebido por par�metro no Banco de Dados
	 */
	public boolean removeAluno(Aluno aluno) {
		if(validaAluno(aluno)) {
			return alunoDAO.removeUsuario(aluno);
		}
		return false;
		
	}
	/**
	 * M�todo respons�vel por criar um objeto do tipo Funcion�rio no sistema
	 * @param f, que � um objeto do tipo Funcion�rio
	 * @return false, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso haja algum problema durante a inser��o do objeto passado por par�metro no banco de dados.
	 * @return true, caso haja sucesso na inser��o do objeto Funcion�rio passado por par�metro no Banco de Dados
	 */
	public boolean createFuncionario(Funcionario f) {
		if(validarFuncionario(f)) {
			return funcionarioDAO.createUsuario(f);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por atualizar um objeto do tipo Funcion�rio no sistema
	 * @param f, que � um objeto do tipo Funcion�rio
	 * @return false, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso haja algum problema durante a atualiza��o do objeto passado por par�metro no banco de dados.
	 * @return true, caso haja sucesso na atualiza��o do objeto Funcion�rio passado por par�metro no Banco de Dados
	 */
	public boolean updateFuncionario(Funcionario f) {
		if(validarFuncionario(f)) {
			return funcionarioDAO.updateUsuario(f);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por remover um objeto do tipo Funcion�rio no sistema
	 * @param f, que � um objeto do tipo Funcion�rio
	 * @return false, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso haja algum problema durante a remo��o do objeto passado por par�metro no banco de dados.
	 * @return true, caso haja sucesso na remo��o do objeto Funcion�rio passado por par�metro no Banco de Dados
	 */
	public boolean removeFuncionario(Funcionario f) {
		if(validarFuncionario(f)) {
			return funcionarioDAO.removeUsuario(f);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por realizar uma busca de um ou mais objetos do tipo Funcion�rio no sistema
	 * @param f, que � um objeto do tipo Funcion�rio
	 * @return null, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso a busca do objeto passado por par�metro no banco de dados n�o tenha sucesso.
	 * @return ArrayList<Funcionario>, caso haja sucesso na busca de um ou mais objetos do tipo Funcion�rio passado por par�metro no Banco de Dados
	 */
	public ArrayList<Funcionario> searchFuncionario(Funcionario f) {
		if(validarFuncionario(f)) {
			return funcionarioDAO.searchUsuario(f);
		}
		return null;
	}

	public boolean createItemAcervo(Item_Acervo itemDao,Acervo item) {
		//TODO Validar dados do item
		return itemDao.createItemAcervo(item);
	}

	public boolean removeItemAcervo(Item_Acervo itemDao,Acervo item) {
		//TODO Validar dados do item
		return itemDao.removeItemAcervo(item);
	}

	public boolean updateItemAcervo(Item_Acervo itemDao,Acervo item) {
		//TODO Validar dados do item
		return itemDao.updateItemAcervo(item);
	}

	public ArrayList<Acervo> searchItemAcervo(Item_Acervo itemDao,Acervo item) {
		//TODO Validar dados do item
		return itemDao.searchItemAcervo(item);
	}
	private boolean validarFuncionario(Funcionario funcionario) {
		if(funcionario == null){
			return false;
		}
		if(funcionario.getCpf()<=0||
				funcionario.getEmail() == null || funcionario.getEmail().equals("")||
				funcionario.getEndereco()==null || funcionario.getEndereco().equals("")||
				funcionario.getNaturalidade()==null || funcionario.getNaturalidade().equals("")||
				funcionario.getNomeCompleto()==null || funcionario.getNomeCompleto().equals("")||
				funcionario.getRg()<=0 ||
				funcionario.getSenhaAcesso() == null || funcionario.getSenhaAcesso().equals("") ||
				funcionario.getTelefone()<=0){
			return false;
		}
		return true;
	}
}
