package br.uepb.model.usuarios;

import java.util.ArrayList;

import br.uepb.dao.AreaConhecimentoDAO;
import br.uepb.dao.AutorDAO;
import br.uepb.dao.CursoDAO;
import br.uepb.dao.EditoraDAO;
import br.uepb.dao.Item_Acervo;
import br.uepb.dao.usuarios.FuncionarioDAO;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Autor;
import br.uepb.model.Curso;
import br.uepb.model.Editora;
import br.uepb.model.acervo.IFAcervo;

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
	private AreaConhecimentoDAO areaDAO;
	private CursoDAO cursoDAO;
	private EditoraDAO editoraDAO;
	private AutorDAO autorDAO;
	private String nomeUsuario;
	
	
	public Administrador() {
		this.funcionarioDAO = new FuncionarioDAO();
		this.areaDAO = new AreaConhecimentoDAO();
		this.cursoDAO = new CursoDAO();
		this.editoraDAO = new EditoraDAO();
		this.autorDAO = new AutorDAO();
	}

	/**
	 * M�todo construtor da classe Administrador
	 * @param nomeUsuario, que � o nome de usu�rio do Administrador no sistema 
	 */
	public Administrador(String nomeUsuario) {
		setNomeUsuario(nomeUsuario);
		this.funcionarioDAO = new FuncionarioDAO();
		this.areaDAO = new AreaConhecimentoDAO();
		this.cursoDAO = new CursoDAO();
		this.editoraDAO = new EditoraDAO();
		this.autorDAO = new AutorDAO();
	}
	
	public Administrador(int cpf, String nomeCompleto, int rg, String naturalidade, String endereco, int telefone,
			String email, String senhaAcesso,String nomeUsuario) {
		super(cpf,nomeCompleto,rg,naturalidade,endereco,telefone,email,senhaAcesso);
		setNomeUsuario(nomeUsuario);
		this.funcionarioDAO = new FuncionarioDAO();
		this.areaDAO = new AreaConhecimentoDAO();
		this.cursoDAO = new CursoDAO();
		this.editoraDAO = new EditoraDAO();
		this.autorDAO = new AutorDAO();
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
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER FUNCIONARIO - - - - - - - - - - - - - - - - - - - - - - -
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
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER ACERVO - - - - - - - - - - - - - - - - - - - - - - -
	/**
	 * M�todo respons�vel por inserir um objeto que implementa a interface Acervo no banco de dados
	 * @param itemDao, que � um objeto especifica qual o tipo de objeto DAO
	 * @param item, que � o objeto que deve ser salvo no banco
	 * @return true, se a inser��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean createItemAcervo(Item_Acervo itemDao,IFAcervo item) {
		if(item.validaItem()) {
			return itemDao.createItemAcervo(item);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por remover um objeto do acervo do banco de dados do sistema
	 * @param itemDao, que � um objeto especifica qual o tipo de objeto DAO
	 * @param item, que � o objeto que deve ser salvo no banco
	 * @return true, se a remo��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean removeItemAcervo(Item_Acervo itemDao,IFAcervo item) {
		if(item.validaItem()) {
			return itemDao.removeItemAcervo(item);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por atualizar um objeto do acervo do banco de dados do sistema
	 * @param itemDao, que � um objeto especifica qual o tipo de objeto DAO
	 * @param item, que � o objeto que deve ser salvo no banco
	 * @return true, se a atualiza��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean updateItemAcervo(Item_Acervo itemDao,IFAcervo item) {
		if(item.validaItem()) {
			return itemDao.updateItemAcervo(item);
		}
		return false;
	}
	/**
	 * M�todo respons�vel por realizar uma busca de um ou mais objetos de acervo do banco de dados do sistema
	 * @param itemDao, que � um objeto especifica qual o tipo de objeto DAO
	 * @param item, que � o objeto que armazenar dados que devem ser buscados
	 * @return null, caso haja algum problema na valida��o do objeto recebido por par�metro ou caso a busca do objeto passado por par�metro no banco de dados n�o tenha sucesso.
	 * @return ArrayList<IFAcervo>, caso haja sucesso na busca de um ou mais objetos do acervo do banco de dados passado por par�metro
	 */
	public ArrayList<IFAcervo> searchItemAcervo(Item_Acervo itemDao,IFAcervo item) {
		if(item.validaItem()) {
			return itemDao.searchItemAcervo(item);
		}
		return null;
	}
	/**
	 * M�todo respons�vel por validar os parametros dos objetos do tipo Funcionario
	 * @param funcionario, que � o objeto do tipo Funcionario que deve ser validado
	 * @return true, se os parametros do funcionario estiverem validos
	 * @return false, se algum dos parametros estiverem nulos ou vazios.
	 */
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
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER AREA CONHECIMENTO - - - - - - - - - - - - - - - - - - - - - - -
	public boolean createAreaConhecimento(AreaConhecimento area){
		return areaDAO.createAreaConhecimento(area);
	}	
	public boolean removeAreaConhecimento(AreaConhecimento area){
		return areaDAO.removeAreaConhecimento(area);
	}
	public boolean updateAreaConhecimento(AreaConhecimento area){
		return areaDAO.updateAreaConhecimento(area);
	}
	public ArrayList<AreaConhecimento> searchAreaConhecimento(AreaConhecimento area){
		return areaDAO.searchAreaConhecimento(area);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER AREA CURSO - - - - - - - - - - - - - - - - - - - - - - -
	public boolean createCurso(Curso curso){
		return cursoDAO.createCurso(curso);
	}	
	public boolean removeCurso(Curso curso){
		return cursoDAO.removeCurso(curso);
	}
	public boolean updateCurso(Curso curso){
		return cursoDAO.updateCurso(curso);
	}
	public ArrayList<Curso> searchCurso(Curso curso){
		return cursoDAO.searchCurso(curso);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER AUTOR - - - - - - - - - - - - - - - - - - - - - - -
	public boolean createAutor(Autor autor){
		return autorDAO.createAutor(autor);
	}
	public boolean removeAutor(Autor autor){
		return autorDAO.removeAutor(autor);
	}
	public boolean updateAutor(Autor autor){
		return autorDAO.updateAutor(autor);
	}
	public ArrayList<Autor> searchAutor(Autor autor){
		return autorDAO.searchAutor(autor);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER EDITORA - - - - - - - - - - - - - - - - - - - - - - -
	public boolean createEditora(Editora editora){
		return editoraDAO.createEditora(editora);
	}
	public boolean removeEditora(Editora editora){
		return editoraDAO.removeEditora(editora);
	}
	public boolean updateEditora(Editora editora){
		return editoraDAO.updateEditora(editora);
	}
	public ArrayList<Editora> searchEditora(Editora editora){
		return editoraDAO.searchEditora(editora);
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
}

