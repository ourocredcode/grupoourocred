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
import br.com.sgo.modelo.TipoDadoBd;

@Component
public class TipoDadoBdDao extends Dao<TipoDadoBd> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTiposDado;

	public TipoDadoBdDao(Session session, ConnJDBC conexao) {
		super(session, TipoDadoBd.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<TipoDadoBd> buscaTiposDado(String nome){

		String sql = "select TIPODADOBD.tipodadobd_id, TIPODADOBD.nome from TIPODADOBD (NOLOCK) " +
				"	WHERE TIPODADOBD.nome like ? ";
		this.conn = this.conexao.getConexao();

		Collection<TipoDadoBd> tiposDadoBd = new ArrayList<TipoDadoBd>();
		try {

			this.stmt = conn.prepareStatement(sql);			

			this.stmt.setString(1,"%"+  nome + "%");			
			
			this.rsTiposDado = this.stmt.executeQuery();

			while (rsTiposDado.next()) {
				TipoDadoBd tipoDadoBd = new TipoDadoBd();

				tipoDadoBd.setTipoDadoBd_id(rsTiposDado.getLong("tipodadobd_id"));				
				tipoDadoBd.setNome(rsTiposDado.getString("nome"));

				tiposDadoBd.add(tipoDadoBd);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(conn);
		return tiposDadoBd;
	}

}
