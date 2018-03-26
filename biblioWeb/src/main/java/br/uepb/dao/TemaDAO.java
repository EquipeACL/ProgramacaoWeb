package br.uepb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.model.Tema;

public class TemaDAO {
	
	private Connection con;
	private static final Logger logger = LogManager.getLogger(OrientadorDAO.class);
	
	public TemaDAO() throws Exception{
		con = Conexao.iniciarConexao();
	}
	
	@SuppressWarnings("finally")
	public boolean createTema(Tema tema) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("INSERT INTO tema(nome) VALUES (?)");
			stmt.setString(1, tema.getNome());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("TemaDAO: Erro na inser��o "+e);
			return false;
		} finally{
			try {
				con.close();
				stmt.close();
				logger.info("TemaDAO: Conex�o Fechada");
				return true;
			} catch (SQLException e) {
				logger.error("TemaDAO: Erro ao fechar a conex�o "+e);
				return false;
			}
		}
	}
	
	@SuppressWarnings("finally")
	public boolean removeTema(Tema tema) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("DELETE FROM tema WHERE id = ?");
			stmt.setInt(1, tema.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("TemaDAO: Erro na remo��o"+e);
			return false;
		} finally {
			try {
				con.close();
				stmt.close();
				logger.info("TemaDAO: Conex�o Fechada");
				return true;
			} catch (SQLException e) {
				logger.error("TemaDAO: Erro ao fechar a conex�o "+e);
				return false;
			}
		}
			
	}
	
	@SuppressWarnings("finally")
	public boolean updateTema(Tema tema) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("UPDATE tema SET nome = ? WHERE id = ?");
			stmt.setString(1, tema.getNome());
			stmt.setInt(2, tema.getId());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			logger.error("TemaDAO: Erro na atualiza��o "+e);
			return false;
		} finally {
			try {
				con.close();
				stmt.close();
				logger.info("TemaDAO: Conex�o Fechada");
				return true;
			} catch (SQLException e) {
				logger.error("TemaDAO: Erro ao fechar a conex�o "+e);
				return false;
			}
		}	
	}
	
	public ArrayList<Tema> searchTema(Tema tema){
		
		ArrayList<Tema> listaTema = new ArrayList<Tema>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.prepareStatement("SELECT * FROM tema WHERE nome LIKE '%?%'");
			stmt.setString(1, tema.getNome());
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Tema t = new Tema(rs.getInt("id"),rs.getString("nome"));
				listaTema.add(t);	
			}
			
		} catch (SQLException e) {
			logger.error("TemaDAO: Erro na busca "+e);
		} finally {
			try {
				con.close();
				stmt.close();
				logger.info("TemaDAO: Conex�o Fechada");
				
			} catch (SQLException e) {
				logger.error("TemaDAO: Erro ao fechar a conex�o "+e);
			}
		}
		
		return listaTema;
	}
}
