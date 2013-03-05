package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ColunaBd;

@Component
public class ColunaBdDao  extends Dao<ColunaBd> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsColunasBd;

	public ColunaBdDao(Session session) {
		super(session, ColunaBd.class);
		this.session = session;
	}
	
	public ColunaBd buscaColunasBd(Long empresa_id, Long organizacao_id, Long tabelaBd_id, Long elementoBd_id){

		String sql = "select COLUNABD.colunabd_id from COLUNABD (NOLOCK) " +
				"		  WHERE COLUNABD.empresa_id = ? AND" +
				"		  	    COLUNABD.organizacao_id = ? AND" +
				"		 	    COLUNABD.tabelabd_id = ? AND" +
				"	      		COLUNABD.elementobd_id = ? ";

		this.conn = this.conexao.getConexao();

		ColunaBd colunaBd = new ColunaBd();

		try {

			this.stmt = conn.prepareStatement(sql);	

			this.stmt.setLong(1,empresa_id);			
			this.stmt.setLong(2,organizacao_id);
			this.stmt.setLong(3,tabelaBd_id);
			this.stmt.setLong(4,elementoBd_id);

			this.rsColunasBd = this.stmt.executeQuery();

			while (rsColunasBd.next()) {

				colunaBd.setColunabd_id(rsColunasBd.getLong("colunabd_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(conn);

		return colunaBd;

	}

}
