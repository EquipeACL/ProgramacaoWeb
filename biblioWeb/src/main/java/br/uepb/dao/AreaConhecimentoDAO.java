package br.uepb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.interfaces.DAO_Dependencia;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;
/**
 * Essa classe � respons�vel por se conectar com o Banco de Dados para opera��es de inserir, atualizar, remover e buscar objetos do tipo AreaConhecimento
 * @author EquipeACL
 */
public class AreaConhecimentoDAO implements DAO_Dependencia<AreaConhecimento>{
	
	private Connection con;
	private static final Logger  logger = LogManager.getLogger(AreaConhecimentoDAO.class);
	
	
	/**
	 * M�todo para inserir Area de Conhecimento no banco de dados
	 * @param area, objeto do tipo AreaConhecimento
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true, se a opera��o de inser��o for bem sucedida
	 * @return false, se ocorrer algum problema na inser��o
	 */	
	@SuppressWarnings("finally")
	public boolean createItemDependencia(AreaConhecimento area) {
		
		PreparedStatement stmt = null;

		try {
			con = Conexao.iniciarConexao();
			stmt = con.prepareStatement("INSERT INTO area_conhecimento(nome) VALUES(?)");
			stmt.setString(1, area.getNome());			
			stmt.executeUpdate();
			return true;
		} catch	(SQLException e)	{
			logger.error("Erro durante a inser��o ",e);
		} catch (Exception e) {
			logger.error("Erro durante a inser��o ",e);
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na inser��o");
			
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na inser��o ",e);
			}
		}
		return false;
	}
    
	/**
	 * M�todo para pesquisar Area de Conhecimento no banco de dados
	 * @param area, objeto do tipo AreaConhecimento.
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return ArrayList<AreaConhecimento> listaAreas, lista de areas do conhecimento retornados pela busca.
	 */
	
	public ArrayList<AreaConhecimento> searchItemDependencia(String nome){
		ArrayList<AreaConhecimento> listaAreas = new ArrayList<AreaConhecimento>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = Conexao.iniciarConexao();
			//stmt = con.prepareStatement("SELECT A.id AS id_area, A.nome AS nome_area, T.id AS id_tema, T.nome as nome_tema FROM area_conhecimento AS A INNER JOIN tema AS T ON A.tema_id = T.id WHERE A.nome LIKE '%?%'");
			stmt = con.prepareStatement("SELECT * from area_conhecimento where nome LIKE ?");
			stmt.setString(1,"%"+nome+"%");			
			rs = stmt.executeQuery();			
			while(rs.next()) {
				AreaConhecimento a = new AreaConhecimento(rs.getInt("id"),rs.getString("nome"));				
				listaAreas.add(a);
			}
		} catch (SQLException e) {
			logger.error("Erro durante a busca ",e);
			return null;
		} catch (Exception e) {
			logger.error("Erro durante a busca ",e);
			return null;
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na busca");
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na busca ",e);	
			}		
		}
		
		return listaAreas;
		
	}
	
	/**
	 * M�todo para remover Area de Conhecimento do banco de dados
	 * @param area, objeto do tipo AreaConhecimento.
	 * @throws SQLException,
	 * @throws JavaLangException
	 * @return true, se a opera��o de remo��o for bem sucedida.
	 * @return false, se ocorrer algum erro na remo��o.
	 */	
	@SuppressWarnings("finally")
	public boolean removeItemDependencia(AreaConhecimento area) {
		
		PreparedStatement stmt = null;

		try {
			con = Conexao.iniciarConexao();
			stmt = con.prepareStatement("DELETE FROM area_conhecimento WHERE id =?");
			stmt.setInt(1, area.getId());		
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Erro durante a remo��o ",e);
			
		} catch (Exception e) {
			logger.error("Erro durante a remo��o ",e);
		} finally {
			try {
				stmt.close();
				con.close();
				logger.info("Conex�o Fechada na remo��o");
			}catch(SQLException e){
				logger.error("Erro ao fechar a conex�o na remo��o ",e);
			}
		}
		return false;

	}
	
	/**
	 * M�todo para atualizar Area de Conhecimento no banco de dados
	 * @param area, objeto do tipo AreaConhecimento.
	 * @throws SQLException
	 * @throws JavaLangException
	 * @return true, se a opera��o de atualiza��o for bem sucedida
	 * @return false, se ocorrer algum erro na atualiza��o.
	 */	
	@SuppressWarnings("finally")
	public boolean updateItemDependencia(AreaConhecimento area) {
		
		PreparedStatement stmt = null;
		
		try {
			con = Conexao.iniciarConexao();
			stmt = con.prepareStatement("UPDATE area_conhecimento SET nome = ? WHERE id = ?");
			stmt.setString(1, area.getNome());
			stmt.setInt(2, area.getId());			
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Erro ao atualizar ",e);
		} catch (Exception e) {
			logger.error("Erro ao atualizar ",e);
		} finally {
			
			try {
				con.close();
				stmt.close();
				logger.info("Conex�o fechada na atualiza��o");
			} catch (SQLException e) {
				logger.error("Erro ao fechar conex�o na atualiza��o ",e);
			}
		}
		return false;
	}

}
