package br.uepb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.model.Autor;

public class AutorDAO {
	private Connection con;
	private static final Logger logger = LogManager.getLogger(AutorDAO.class);
	
	/**
	 * M�todo para inserir Autor no banco de dados
	 * @param autor
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return
	 */
	
	@SuppressWarnings("finally")
	public boolean createAutor(Autor autor){
		String sql = "insert into autor(nome)values(?)";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, autor.getNome());
			stmt.execute();
			return true;
		} catch(SQLException e) {
			if(e.getClass().equals(new com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException().getClass())){
				logger.error("Erro na inser��o - Parametros null",e);
				return false;
			}
			logger.error("Erro na inser��o ",e);
			return false;
		} catch (Exception e) {
			if(e.getClass().equals(new java.lang.NullPointerException().getClass())){
				logger.error("Erro na inser��o - Parametros null",e);
				return false;
			}
			logger.error("Erro na inser��o ",e);
			return false;
		}
		finally {
			try {
				con.close();
				logger.info("Conex�o fechada na inser��o");
				//return true;//Complicado esse return aqui, porque se inserir mas na hora de fechar a conexao der falha vai retorna false e a interpreta��o de que n�o foi inserido
				//E quando acontece uma exce��o ba inser��o o m�todo return true pois consegue fechar a conex�o, mas deveria retorna false pois n�o inseriu.
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na inser��o ",e);
				//return false;
			}
		}
		
	}
	
	/**
	 * M�todo para remover Autor do banco de dados
	 * @param autor
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true or false
	 */
	
	@SuppressWarnings("finally")
	public boolean removeAutor(Autor autor) {
		String sql = "delete from autor where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1,autor.getId());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			logger.error("Erro na remo��o ",e);
		} catch (Exception e) {
			logger.error("Erro na remo��o ",e);
		}finally {
			try {
				con.close();
				logger.info("Conex�o fechada na remo��o");			
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na remo��o ",e);
			}
		}
		return false;
	}
	
	/**
	 * M�todo para atualizar Autor no banco de dados
	 * @param autor
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true or false
	 */
	
	@SuppressWarnings("finally")
	public boolean updateAutor(Autor autor) {
		String sql = "update autor set nome=? where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,autor.getNome());
			stmt.setInt(2, autor.getId());
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
				//return true;//Mesmo problema do insert

			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na atualiza��o ",e);
				//return false;
			}
		}
		return false;
	}
	
	/**
	 * M�todo para pesquisar Autor no banco de dados
	 * @param autor
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return ArrayList<Autor> autores
	 */
	
	public ArrayList<Autor> searchAutor(Autor autor) {//busca varios autores pelo nome
		String sql = "select * from autor where nome like ?";
		ArrayList<Autor> autores = new ArrayList<Autor>();
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"%"+autor.getNome()+"%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
	            Autor a = new Autor();
	            a.setId(rs.getInt("id"));
	            a.setNome(rs.getString("nome"));
	            autores.add(a);
	        }
		} catch (SQLException e) {
			logger.error("Erro na busca ",e);
		} catch (Exception e) {
			logger.error("Erro na busca ",e);
		}finally {
			try {
				con.close();
				logger.info("Conexao fechada na busca");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada ",e);
			}
		}
		return autores;
	}
	
	/**
	 * M�todo para pesquisar Autor no banco de dados
	 * @param autor
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return autor
	 */
	
	public Autor searchAutor(int id_autor) {//busca um unico autor pelo id
		String sql = "select * from autor where id=?";
		Autor autor = new Autor();
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1,id_autor);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){	            
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
	        }
		} catch (SQLException e) {
			logger.error("Erro na busca",e);
		} catch (Exception e) {
			logger.error("Erro na busca",e);
		}finally {
			try {
				con.close();
				logger.info("Conexao fechada na busca");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na busca ",e);
			}
		}
		return autor;
	}
	
	/**
	 * M�todo para pesquisar Autor pelo ISBN no banco de dados
	 * @param isbn
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return ArrayList<Autor> autores
	 */
	public ArrayList<Autor> buscarAutoresPorISBN(long isbn) {
		
		String sql = "select * from autor_has_livro inner join autor on autor_id=autor.id where livro_isbn=?";
		ArrayList<Autor> autores = new ArrayList<Autor>();
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1,isbn);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
	            Autor a = new Autor();
	            a.setId(rs.getInt("autor_id"));
	            a.setNome(rs.getString("nome"));
	            autores.add(a);
	        }
		} catch (SQLException e) {
			logger.error("Erro na busca ",e);
		} catch (Exception e) {
			logger.error("Erro na busca",e);
		}finally {
			try {
				con.close();
				logger.info("Conexao fechada na busca");
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada na busca ",e);
			}
		}
		return autores;
	}
}
