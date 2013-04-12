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
import br.com.sgo.modelo.MeioPagamento;

@Component
public class MeioPagamentoDao extends Dao<MeioPagamento> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsMeioPagamento;

	private final String sqlMeioPagamento = "SELECT MEIOPAGAMENTO.meiopagamento_id, MEIOPAGAMENTO.nome, MEIOPAGAMENTO.empresa_id"
			+ ", MEIOPAGAMENTO.organizacao_id FROM MEIOPAGAMENTO (NOLOCK)";

	public MeioPagamentoDao(Session session, ConnJDBC conexao) {
		super(session, MeioPagamento.class);
		this.conexao = conexao;
	}

	public Collection<MeioPagamento> buscaAllMeioPagamento() {
		String sql = sqlMeioPagamento;
		this.conn = this.conexao.getConexao();
		Collection<MeioPagamento> meiospagamento = new ArrayList<MeioPagamento>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsMeioPagamento = this.stmt.executeQuery();
			while (rsMeioPagamento.next()) {
				MeioPagamento meioPagamento = new MeioPagamento();
				meioPagamento.setMeiopagamento_id(rsMeioPagamento
						.getLong("meiopagamento_id"));
				meioPagamento.setNome(rsMeioPagamento.getString("nome"));
				meiospagamento.add(meioPagamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsMeioPagamento, stmt, conn);
		return meiospagamento;
	}

	public MeioPagamento buscaMeioPagamentoByEmpOrgAgeNum(Long empresa_id,
			Long organizacao_id, String nome) {
		String sql = sqlMeioPagamento;
		if (empresa_id != null)
			sql += " WHERE MEIOPAGAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND MEIOPAGAMENTO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (MEIOPAGAMENTO.nome like ?)";
		this.conn = this.conexao.getConexao();
		MeioPagamento meioPagamento = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsMeioPagamento = this.stmt.executeQuery();
			while (rsMeioPagamento.next()) {
				meioPagamento = new MeioPagamento();
				meioPagamento.setMeiopagamento_id(rsMeioPagamento
						.getLong("meiopagamento_id"));
				meioPagamento.setNome(rsMeioPagamento.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsMeioPagamento, stmt, conn);
		return meioPagamento;
	}

}