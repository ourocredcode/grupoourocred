package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ElementoBd;

@Component
public class ElementoBdDao extends Dao<ElementoBd> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsElementos;

	public ElementoBdDao(Session session,ConnJDBC conexao) {
		super(session, ElementoBd.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<ElementoBd> buscaElementos(String nomecolunabd){

		String sql = "select ELEMENTOBD.elementobd_id, ELEMENTOBD.nomecolunabd from ELEMENTOBD (NOLOCK) " +
				"	WHERE ELEMENTOBD.nomecolunabd like ? ";
		this.conn = this.conexao.getConexao();

		Collection<ElementoBd> elementosbd = new ArrayList<ElementoBd>();
		try {

			this.stmt = conn.prepareStatement(sql);			

			this.stmt.setString(1,"%"+  nomecolunabd + "%");			
			
			this.rsElementos = this.stmt.executeQuery();

			while (rsElementos.next()) {

				ElementoBd elementobd = new ElementoBd();

				elementobd.setElementoBd_id(rsElementos.getLong("elementobd_id"));				
				elementobd.setNomeColunaBd(rsElementos.getString("nomecolunabd"));

				elementosbd.add(elementobd);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsElementos, stmt, conn);

		return elementosbd;

	}

}
