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
import br.com.sgo.modelo.Janela;

@Component
public class JanelaDao extends Dao<Janela> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsJanelas;

	public JanelaDao(Session session, ConnJDBC conexao) {
		super(session, Janela.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<Janela> buscaTabelas(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select JANELA.janela_id, JANELA.nome from JANELA (NOLOCK) WHERE JANELA.empresa_id = ? AND JANELA.organizacao_id = ? AND JANELA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Janela> janelas = new ArrayList<Janela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsJanelas = this.stmt.executeQuery();

			while (rsJanelas.next()) {
				Janela janela = new Janela();

				janela.setJanela_id(rsJanelas.getLong("janela_id"));
				janela.setNome(rsJanelas.getString("nome"));

				janelas.add(janela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsJanelas, stmt, conn);

		return janelas;

	}

}
