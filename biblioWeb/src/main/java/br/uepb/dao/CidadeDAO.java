package br.uepb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.model.Cidade;

public class CidadeDAO {
	
	private Connection con;
	private static final Logger  logger = LogManager.getLogger(CidadeDAO.class);
	
	/**
	 * M�todo Construtor
	 * @throws Exception
	 */
	public CidadeDAO() throws Exception {
		con = Conexao.iniciarConexao();
	}
	
	/**
	 * M�todo para inserir Cidade no banco de dados
	 * @param cidade
	 * @throws SQLException
	 * @return true or false 
	 */
	
	@SuppressWarnings("finally")
	public boolean createCidade(Cidade cidade) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("INSERT INTO cidade (Codigo,Nome,Uf) values (?,?,?)");
			stmt.setInt(1,cidade.getCodigo());
			stmt.setString(2, cidade.getNome());
			stmt.setString(3,cidade.getUf());
			
			stmt.executeUpdate();
			
		} catch	(SQLException e)	{
			logger.error("Erro durante a inser��o "+e);
			return false;
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na inser��o");
				return true;
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na inser��o "+e);
				return false;
			}
		}	
	}
	
	/**
	 * M�todo para pesquisar Cidade no banco de dados
	 * @param cidade
	 * @throws SQLException
	 * @return ArrayList<Cidade> listaCidades
	 */
	
	public ArrayList<Cidade> searchCidade(Cidade cidade) {
		
		ArrayList<Cidade> listaCidades = new ArrayList<Cidade>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement("SELECT * FROM cidades WHERE nome LIKE '%?%'");
			stmt.setString(1, cidade.getNome());
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Cidade c = new Cidade(
						rs.getInt("Id"),
						rs.getInt("Codigo"),
						rs.getString("Nome"),
						rs.getString("Uf"));
				listaCidades.add(c);
			}
		
		} catch (SQLException e) {
			logger.error("Erro durante a busca "+e);
			return null;
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na busca");
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na busca "+e);
			}
		}
		
		return listaCidades;
	}
	
	/**
	 * M�todo para remover Cidade do banco de dados
	 * @param cidade
	 * @throws SQLException
	 * @return true or false 
	 */
	
	@SuppressWarnings("finally")
	public boolean removeCidade(Cidade cidade) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("DELETE FROM cidade WHERE Codigo=? AND Nome=? AND Uf=?");
			stmt.setInt(1, cidade.getCodigo());
			stmt.setString(2, cidade.getNome());
			stmt.setString(3, cidade.getUf());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("Erro durante a remo��o da cidade "+e);
			return false;
			
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na remo��o");
				return true;
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na remo��o "+e);
				return false;
			}
		}		
	}
	
	/**
	 * M�todo para atualizar Cidade no banco de dados
	 * @param cidade
	 * @throws SQLException
	 * @return true or false 
	 */
	
	@SuppressWarnings("finally")
	public boolean updateCidade(Cidade cidade) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("UPDATE cidade SET Codigo=?, Nome=?, Uf=? WHERE id=?");
			stmt.setInt(1, cidade.getCodigo());
			stmt.setString(2, cidade.getNome());
			stmt.setString(3, cidade.getUf());
			stmt.setInt(4, cidade.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("Erro durante a atualiza��o da cidade "+e);
			return false;
			
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na atualiza��o");
				return true;
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na atualiza��o "+e);
				return false;
			}
		}		
	}
	
}
