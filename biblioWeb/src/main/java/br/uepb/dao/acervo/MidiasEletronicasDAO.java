package br.uepb.dao.acervo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.uepb.dao.Conexao;
import br.uepb.dao.Item_Acervo;
import br.uepb.model.acervo.Livro;
import br.uepb.model.acervo.Midias_Eletronicas;
import br.uepb.model.enums.Tipo_midia;
/**
 * Essa classe � respons�vel por se conectar com o Banco de Dados para opera��es de inserir, atualizar, remover e buscar objetos do tipo Midias_Eletronicas
 * Ela implementa a interface Item_Acervo passando o tipo Midias_Eletronicas.
 * @author EquipeACL
 */
public class MidiasEletronicasDAO implements Item_Acervo<Midias_Eletronicas>{
	private Connection con;
	private static final Logger logger = LogManager.getLogger(MidiasEletronicasDAO.class); 
	
	/**
	 * M�todo para inserir uma Midia Eletr�nica no banco de dados
	 * @param midia, objeto do tipo Midias_Eletronicas
	 * @throws SQLException 
	 * @throws JavaLangException 
	 * @return true, se a opera��o de inser��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean createItemAcervo(Midias_Eletronicas midia) {
		
		String sql = "insert into midia(titulo,tipo,data_gravacao) values (?,?,?)";
		try {
			con = Conexao.iniciarConexao(); 
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, midia.getTitulo());
			stmt.setString(2,midia.getTipo().toString());
			stmt.setDate(3,new java.sql.Date(midia.getData_gravacao().getTime()));
			stmt.execute();
			return true;
		}catch (SQLException e) {
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
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada",e);
			}
		}
		return false;
	}

	/**
	 * M�todo para remover uma Midia Eletr�nica do banco de dados
	 * @param midia, objeto do tipo Midias_Eletronicas
	 * @throws SQLException 
	 * @throws JavaLangException 
	 * @return true, se a opera��o de remo��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean removeItemAcervo(Midias_Eletronicas midia) {
		String sql = "delete from midia where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, midia.getId());
			stmt.execute();
			return  true;
		} catch (SQLException e) {
			logger.error("Erro na remo��o",e);
		} catch (Exception e) {
			logger.error("Erro na remo��o",e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada",e);
			}
		}
		return false;
	}
	/**
	 * M�todo para atualizar Midia Eletr�nica no banco de dados
	 * @param midia, objeto do tipo Midias_Eletronicas
	 * @throws SQLException 
	 * @throws JavaLangException 
	 * @return true, se a opera��o de atualiza��o for bem sucedida
	 * @return false, se ocorrer algum erro na opera��o
	 */
	public boolean updateItemAcervo(Midias_Eletronicas midia) {
		String sql = "update midia set titulo=?,tipo=?,data_gravacao=? where id=?";
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, midia.getTitulo());
			stmt.setString(2,midia.getTipo().toString());
			stmt.setDate(3,new java.sql.Date(midia.getData_gravacao().getTime()));
			stmt.setInt(4, midia.getId());
			stmt.execute();
			return true;
		} catch (SQLException e) {
			logger.error("Erro na atualiza��o",e);
		} catch (Exception e) {
			logger.error("Erro na atualiza��o",e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada",e);
			}
		}
		return false;
	}
	/**
	 * M�todo para pesquisar Midia Eletr�nica no banco de dados
	 * @param midia, objeto do tipo Midias_Eletronicas
	 * @throws SQLException 
	 * @throws JavaLangException 
	 * @return ArrayList<Midias_Eletronicas> midias, lista de midias retornadas pela busca
	 */
	public ArrayList<Midias_Eletronicas> searchItemAcervo(Midias_Eletronicas midia) {
		String sql = "select * from midia where titulo like ?";
		ArrayList<Midias_Eletronicas> midias = new ArrayList<Midias_Eletronicas>();
		try {
			con = Conexao.iniciarConexao();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1,"%"+midia.getTitulo()+"%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
	            Midias_Eletronicas m = new Midias_Eletronicas();
	            m.setId(rs.getInt("id"));
	            m.setTitulo(rs.getString("titulo"));
	            m.setTipo(Enum.valueOf(Tipo_midia.class,rs.getString("tipo")));
	            m.setData_gravacao(rs.getDate("data_gravacao"));
	            midias.add(m);
	        }
		} catch (SQLException e) {
			logger.error("Erro na busca",e);
		} catch (Exception e) {
			logger.error("Erro na busca",e);
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Conexao n�o pode ser fechada",e);
			}
		}
		return midias;
	}

	
}
