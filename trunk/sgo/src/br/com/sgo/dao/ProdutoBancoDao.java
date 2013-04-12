package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ProdutoBanco;

@Component
public class ProdutoBancoDao extends Dao<ProdutoBanco> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;

	public ProdutoBancoDao(Session session, ConnJDBC conexao) {

		super(session, ProdutoBanco.class);
		this.session = session;
		this.conexao = conexao;

	}

	public void insert(ProdutoBanco produtoBanco) throws SQLException {

		String sql = "INSERT INTO PRODUTOBANCO " + "	(produto_id, "
				+ "	 banco_id ," + "	 empresa_id ," + "	 organizacao_id, "
				+ "    tabela_id," + "    isactive) "
				+ "    VALUES (?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, produtoBanco.getProduto().getProduto_id());
			this.stmt.setLong(2, produtoBanco.getBanco().getBanco_id());
			this.stmt.setLong(3, produtoBanco.getEmpresa().getEmpresa_id());
			this.stmt.setLong(4, produtoBanco.getOrganizacao()
					.getOrganizacao_id());
			this.stmt.setLong(5, produtoBanco.getTabela().getTabela_id());
			this.stmt.setBoolean(6, produtoBanco.getIsActive());

			this.stmt.executeUpdate();

			this.conn.commit();

		} catch (SQLException e) {

			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}
		this.conexao.closeConnection(stmt, conn);

	}

}
