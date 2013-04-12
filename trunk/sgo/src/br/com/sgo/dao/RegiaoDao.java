package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Regiao;

@Component
public class RegiaoDao extends Dao<Regiao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsRegiao;

	public RegiaoDao(Session session, ConnJDBC conexao) {
		super(session, Regiao.class);
		this.conexao = conexao;
	}

	public Regiao buscaPorNome(String nome) {

		String sql = "select REGIAO.regiao_id, REGIAO.nome, REGIAO.chave from REGIAO (NOLOCK) WHERE REGIAO.chave like ?";

		this.conn = this.conexao.getConexao();
		Regiao regiao = new Regiao();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsRegiao = this.stmt.executeQuery();

			while (rsRegiao.next()) {
				regiao.setRegiao_id(rsRegiao.getLong("regiao_id"));
				regiao.setNome(rsRegiao.getString("nome"));
				regiao.setChave(rsRegiao.getString("chave"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsRegiao, stmt, conn);

		return regiao;

	}

}