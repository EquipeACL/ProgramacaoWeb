package br.uepb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.model.AreaConhecimento;
import br.uepb.model.Autor;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;
/**
 * Essa classe � respons�vel por se conectar com o Banco de Dados para opera��es de inserir, atualizar, remover e buscar objetos do tipo Curso
 * @author EquipeACL
 */
public class CursoDAO {
	private Connection con;
	private static final Logger logger = LogManager.getLogger(AutorDAO.class);
	
	/**
	 * M�todo para inserir Curso no banco de dados
	 * @param curso, objeto do tipo Curso
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true, se a opera��o de inser��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */	
	public boolean createCurso(Curso curso){
		String sql = "insert into curso(nome,sigla, tipo,area_conhecimento_id)values(?,?,?,?)";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, curso.getNome());
			stmt.setString(2,curso.getSigla());
			stmt.setString(3,curso.getTipo().toString());
			stmt.setInt(4, curso.getArea().getId());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			if(e.getClass().equals(new com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException().getClass())){
				logger.error("Erro na inser��o - Parametros null",e);
				return false;
			}
			logger.error("Erro na inser��o",e);
		} catch (Exception e) {
			if(e.getClass().equals(new java.lang.NullPointerException().getClass())){
				logger.error("Erro na inser��o - Parametros null",e);
				return false;
			}
			logger.error("Erro na inser��o",e);
		}finally {
			try {
				con.close();
				logger.info("Conex�o fechada na inser��o");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na inser��o",e);
			}
		}
		return false;
	}
	
	/**
	 * M�todo para remover Curso do banco de dados
	 * @param curso, objeto do tipo Curso
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true, se a opera��o de remo��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */	
	public boolean removeCurso(Curso curso){
		String sql = "delete from curso where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,curso.getId());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			logger.error("Erro na remo��o",e);
		} catch (Exception e) {
			logger.error("Erro na remo��o",e);
		}finally {
			try {
				con.close();
				logger.info("Conex�o fechada na remo��o");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na remo��o",e);
			}
		}
		return false;
	}
	
	/**
	 * M�todo para atualizar Curso no banco de dados
	 * @param curso, objeto do tipo Curso
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true, se a opera��o de atualiza��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */	
	public boolean updateCurso(Curso curso){
		String sql = "update curso set nome=?, sigla=?,tipo=?, area_conhecimento_id=? where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, curso.getNome());
			stmt.setString(2, curso.getSigla());
			stmt.setString(3,curso.getTipo().toString());
			stmt.setInt(4, curso.getArea().getId());
			stmt.setInt(5, curso.getId());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			logger.error("Erro na atualiza��o ",e);
		} catch (Exception e) {
			logger.error("Erro na atualiza��o ",e);
		}finally {
			try {
				con.close();
				logger.info("Conex�o fechada na atualiza��o");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na atualiza��o",e);
			}
		}
		return false;
	}
	
	/**
	 * M�todo para pesquisar Curso no banco de dados
	 * @param curso, objeto do tipo Curso
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return ArrayList<Curso> cursos, lista de cursos retornados pela busca
	 */	
	public ArrayList<Curso> searchCurso(Curso curso){
		String sql = "select c.id as 'curso_id', c.nome as 'curso_nome', c.sigla as 'curso_sigla', c.tipo as 'curso_tipo', a.id as 'area_id', a.nome as 'area_nome' from curso as c inner join area_conhecimento as a on c.area_conhecimento_id = a.id where c.nome like ?";
		ArrayList<Curso> listaCursos = new ArrayList<Curso>();
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"%"+curso.getNome()+"%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
	            Curso c = new Curso();
	            c.setId(rs.getInt("curso_id"));
	            c.setNome(rs.getString("curso_nome"));
	            c.setSigla(rs.getString("curso_sigla"));
	            c.setTipo(Enum.valueOf(Tipo_curso.class,rs.getString("curso_tipo")));
	            c.setArea(new AreaConhecimento(rs.getInt("area_id"),rs.getString("area_nome")));
	            listaCursos.add(c);
	        }
		} catch (SQLException e) {
			logger.error("Erro na busca",e);
		} catch (Exception e) {
			logger.error("Erro na busca",e);
		}finally {
			try {
				con.close();
				logger.info("Conex�o fechada na busca");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na busca ",e);
			}
		}
		return listaCursos;
	}
}
