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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.MeioPagamento;
import br.com.sgo.modelo.Organizacao;

@Component
public class MeioPagamentoDao extends Dao<MeioPagamento> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsMeioPagamento;

	private final String sqlMeioPagamento = "SELECT MEIOPAGAMENTO.meiopagamento_id, MEIOPAGAMENTO.nome FROM MEIOPAGAMENTO (NOLOCK) ";

	private final String sqlMeiosPagamento = " SELECT MEIOPAGAMENTO.empresa_id, EMPRESA.nome AS empresa_nome, MEIOPAGAMENTO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
										", MEIOPAGAMENTO.meiopagamento_id, MEIOPAGAMENTO.nome AS meiopagamento_nome, MEIOPAGAMENTO.isactive "+
										" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN MEIOPAGAMENTO (NOLOCK) "+
										" ON EMPRESA.empresa_id = MEIOPAGAMENTO.empresa_id) ON ORGANIZACAO.organizacao_id = MEIOPAGAMENTO.organizacao_id ";

	public MeioPagamentoDao(Session session, ConnJDBC conexao) {

		super(session, MeioPagamento.class);
		this.conexao = conexao;

	}

	public Collection<MeioPagamento> buscaAllMeioPagamento(Long empresa_id, Long organizacao_id) {

		String sql = sqlMeiosPagamento;
		
		if (empresa_id != null)
			sql += " WHERE MEIOPAGAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND MEIOPAGAMENTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<MeioPagamento> meiosPagamento = new ArrayList<MeioPagamento>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsMeioPagamento = this.stmt.executeQuery();

			while (rsMeioPagamento.next()) {

				getMeioPagamento(meiosPagamento);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsMeioPagamento, stmt, conn);
		return meiosPagamento;
	}

	public Collection<MeioPagamento> buscaAllMeioPagamentoByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlMeiosPagamento;
		
		if (empresa_id != null)
			sql += " WHERE MEIOPAGAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND MEIOPAGAMENTO.organizacao_id = ?";
		if (nome != null)
			sql += " AND MEIOPAGAMENTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<MeioPagamento> meiosPagamento = new ArrayList<MeioPagamento>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsMeioPagamento = this.stmt.executeQuery();

			while (rsMeioPagamento.next()) {

				getMeioPagamento(meiosPagamento);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsMeioPagamento, stmt, conn);
		return meiosPagamento;
	}

	public MeioPagamento buscaMeioPagamentoByNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlMeioPagamento;

		if (empresa_id != null)
			sql += " WHERE MEIOPAGAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND MEIOPAGAMENTO.organizacao_id = ?";
		if (nome != null)
			sql += " AND MEIOPAGAMENTO.nome = ? ";

		this.conn = this.conexao.getConexao();

		MeioPagamento meioPagamento = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsMeioPagamento = this.stmt.executeQuery();

			while (rsMeioPagamento.next()) {

				meioPagamento = new MeioPagamento();
				meioPagamento.setMeioPagamento_id(rsMeioPagamento.getLong("meiopagamento_id"));
				meioPagamento.setNome(rsMeioPagamento.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsMeioPagamento, stmt, conn);
		return meioPagamento;
	}

	private void getMeioPagamento(Collection<MeioPagamento> meiosPagamento)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		MeioPagamento meioPagamento = new MeioPagamento();

		empresa.setEmpresa_id(rsMeioPagamento.getLong("empresa_id"));
		empresa.setNome(rsMeioPagamento.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsMeioPagamento.getLong("organizacao_id"));
		organizacao.setNome(rsMeioPagamento.getString("organizacao_nome"));

		meioPagamento.setEmpresa(empresa);
		meioPagamento.setOrganizacao(organizacao);

		meioPagamento.setMeioPagamento_id(rsMeioPagamento.getLong("meiopagamento_id"));
		meioPagamento.setNome(rsMeioPagamento.getString("meiopagamento_nome"));
		meioPagamento.setIsActive(rsMeioPagamento.getBoolean("isactive"));

		meiosPagamento.add(meioPagamento);

	}

}