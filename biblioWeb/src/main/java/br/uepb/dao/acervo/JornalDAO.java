package br.uepb.dao.acervo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import org.apache.log4j.LogManager;

import br.uepb.dao.Conexao;
import br.uepb.dao.Item_Acervo;
import br.uepb.model.acervo.Jornal;

public class JornalDAO implements Item_Acervo<Jornal>{

	private Connection con;
	private static final Logger logger = LogManager.getLogger(JornalDAO.class);
	
	public JornalDAO() throws Exception{
		con = Conexao.iniciarConexao();
	}

	public boolean createItemAcervo(Jornal jornal) {		
		String	sql	=	"insert	into jornal"	+
				"(titulo,data,edicao)"	+ //tirei o id daqui, porque � autoincremento
				"values	(?,?,?)";
		PreparedStatement stmt = null;
		try	{
			//	prepared	statement	para	inser��o
			con = Conexao.iniciarConexao();
			stmt =	con.prepareStatement(sql);
			//	seta	os	valores
			//stmt.setInt(1,jornal.getId()); tirei o id daqui, porque � autoincremento
			stmt.setString(1,jornal.getTitulo());
			stmt.setDate(2, (Date) jornal.getData());
			stmt.setInt(3,jornal.getEdicao());
			//	executa
			stmt.execute();
		}catch	(SQLException	e)	{
			logger.error("JornalDAO: erro na inser��o",e);
			return false;
		}finally {
			try {
				stmt.close();
				con.close();
				logger.info("JornalDAO: Conex�o Fechada");
				return true;
			}catch(SQLException e){
				logger.error("JornalDAO: erro ao fechar conex�o",e);
				return false;
			}
		}

		
	}

	public boolean removeItemAcervo(Jornal jornal) {
		// TODO Auto-generated method stub
		String	sql	= "DELETE FROM jornal WHERE id =?";
				
		PreparedStatement stmt = null;
	    try {
	    	con = Conexao.iniciarConexao();
	    	stmt =	con.prepareStatement(sql);
	    	stmt.setInt(1, jornal.getId());
	    	logger.info("Remo��o feita com sucesso");
	    }catch(SQLException e) {
	    	logger.error("JornalDAO: erro ao fazer a remo��o",e);
	    	return false;
	    }finally {
			try {
				stmt.close();
				con.close();
				logger.info("JornalDAO: Conex�o Fechada");
				return true;
			}catch(SQLException e){
				logger.error("JornalDAO: erro ao fechar conex�o",e);
				return false;
			}
		}
	}

	@SuppressWarnings("finally")
	public boolean updateItemAcervo(Jornal jornal) {
		String	sql	=	"UPDATE jornal set titulo =?,data=?,edicao=?"	+
				"WHERE id =?";
		PreparedStatement stmt = null;
	    try {
	    	con = Conexao.iniciarConexao();
	    	stmt =	con.prepareStatement(sql);
	    	stmt.setString(1, jornal.getTitulo());
	    	stmt.setDate(2, (Date) jornal.getData());
	    	stmt.setInt(3, jornal.getEdicao());
	    	stmt.executeUpdate();
	    }catch(SQLException e) {
	    	logger.error("JornalDAO: erro ao fazer o update",e);
	    }finally {
			try {
				stmt.close();
				con.close();
				logger.info("JornalDAO: Conex�o Fechada");
				return true;
			}catch(SQLException e){
				logger.error("JornalDAO: erro ao fechar conex�o",e);
				return false;
			}
		}

	}

	public ArrayList<Jornal> searchItemAcervo(Jornal jornal) {

		PreparedStatement stmt = null;
		ArrayList <Jornal> jornais = new ArrayList <Jornal>();
		ResultSet rs = null;
		
		try{
			con = Conexao.iniciarConexao();
			stmt = con.prepareStatement("select * from jornal where titulo like ?");
			stmt .setString(1,"%"+ jornal.getTitulo()+ "%");
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Jornal jor = new Jornal();
				jor.setId(rs.getInt("id"));
				jor.setTitulo(rs.getString("titulo"));
				jor.setData((Date)rs.getDate("data"));
				jor.setEdicao(rs.getInt("edicao"));
				
				jornais.add(jornal);
			}
			
		}catch (SQLException e) {
			logger.error("JornalDAO: erro ao fazer a busca",e);
		} catch (Exception e) {
			logger.error("JornalDAO: erro ao abrir a conex�o",e);
			e.printStackTrace();
		}finally {
			try {
				
				rs.close();
				stmt.close();
				con.close();
				logger.info("JornalDAO: Conex�o Fechada");
				
			}catch(SQLException e){
				logger.error("JornalDAO: erro ao fechar conex�o",e);
			}
		}
		return jornais;
	}
}
