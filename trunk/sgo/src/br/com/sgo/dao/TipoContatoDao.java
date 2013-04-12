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
import br.com.sgo.modelo.TipoContato;

@Component
public class TipoContatoDao extends Dao<TipoContato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoContato;

	public TipoContatoDao(Session session, ConnJDBC conexao) {
		super(session, TipoContato.class);
		this.conexao = conexao;
	}

	public Collection<TipoContato> buscaTiposContatos() {

		String sql = "SELECT TIPOCONTATO.tipocontato_id, TIPOCONTATO.nome, TIPOCONTATO.chave FROM TIPOCONTATO (NOLOCK) ";

		this.conn = this.conexao.getConexao();

		Collection<TipoContato> tiposContato = new ArrayList<TipoContato>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsTipoContato = this.stmt.executeQuery();

			while (rsTipoContato.next()) {

				TipoContato tipoContato = new TipoContato();
				tipoContato.setTipoContato_id(rsTipoContato
						.getLong("tipocontato_id"));
				tipoContato.setNome(rsTipoContato.getString("nome"));
				tipoContato.setChave(rsTipoContato.getString("chave"));

				tiposContato.add(tipoContato);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoContato, stmt, conn);

		return tiposContato;

	}
}