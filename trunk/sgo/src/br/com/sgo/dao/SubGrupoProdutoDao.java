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
import br.com.sgo.modelo.SubGrupoProduto;

@Component
public class SubGrupoProdutoDao extends Dao<SubGrupoProduto> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsSubGrupoProdutos;

	public SubGrupoProdutoDao(Session session, ConnJDBC conexao) {

		super(session, SubGrupoProduto.class);
		this.conexao = conexao;

	}

	public Collection<SubGrupoProduto> buscaSubGrupoProdutos(Long empresa_id, Long organizacao_id, String nome) {

		String sql = "select SUBGRUPOPRODUTO.subgrupoproduto_id, SUBGRUPOPRODUTO.nome from SUBGRUPOPRODUTO (NOLOCK) "
				+ "	WHERE SUBGRUPOPRODUTO.empresa_id = ? AND SUBGRUPOPRODUTO.organizacao_id = ? AND SUBGRUPOPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<SubGrupoProduto> subGrupoProdutos = new ArrayList<SubGrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsSubGrupoProdutos = this.stmt.executeQuery();

			while (rsSubGrupoProdutos.next()) {
				SubGrupoProduto subGrupoProduto = new SubGrupoProduto();

				subGrupoProduto.setSubGrupoProduto_id(rsSubGrupoProdutos
						.getLong("subgrupoproduto_id"));
				subGrupoProduto.setNome(rsSubGrupoProdutos.getString("nome"));

				subGrupoProdutos.add(subGrupoProduto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsSubGrupoProdutos, stmt, conn);

		return subGrupoProdutos;

	}

	public Collection<SubGrupoProduto> buscaSubGrupoProdutoByGrupoProduto(Long grupoProduto_id) {

		String sql = "SELECT SUBGRUPOPRODUTO.subgrupoproduto_id, SUBGRUPOPRODUTO.nome "+
					" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN SUBGRUPOPRODUTO (NOLOCK) ON EMPRESA.empresa_id = SUBGRUPOPRODUTO.empresa_id) "+
					" ON ORGANIZACAO.organizacao_id = SUBGRUPOPRODUTO.organizacao_id ";

		if (grupoProduto_id != null)
			sql += " WHERE SUBGRUPOPRODUTO.grupoproduto_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<SubGrupoProduto> subGrupoProdutos = new ArrayList<SubGrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, grupoProduto_id);

			this.rsSubGrupoProdutos = this.stmt.executeQuery();

			while (rsSubGrupoProdutos.next()) {

				SubGrupoProduto subGrupoProduto = new SubGrupoProduto();

				subGrupoProduto.setSubGrupoProduto_id(rsSubGrupoProdutos.getLong("subgrupoproduto_id"));
				subGrupoProduto.setNome(rsSubGrupoProdutos.getString("nome"));

				subGrupoProdutos.add(subGrupoProduto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsSubGrupoProdutos, stmt, conn);

		return subGrupoProdutos;

	}

	public Collection<SubGrupoProduto> buscaSubGrupoProdutos(Long empresa_id,Long organizacao_id) {

		String sql = "select SUBGRUPOPRODUTO.subgrupoproduto_id, SUBGRUPOPRODUTO.nome from SUBGRUPOPRODUTO (NOLOCK) "
				+ "	WHERE SUBGRUPOPRODUTO.empresa_id = ? AND SUBGRUPOPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<SubGrupoProduto> subGrupoProdutos = new ArrayList<SubGrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsSubGrupoProdutos = this.stmt.executeQuery();

			while (rsSubGrupoProdutos.next()) {

				SubGrupoProduto subGrupoProduto = new SubGrupoProduto();

				subGrupoProduto.setSubGrupoProduto_id(rsSubGrupoProdutos.getLong("subgrupoproduto_id"));
				subGrupoProduto.setNome(rsSubGrupoProdutos.getString("nome"));

				subGrupoProdutos.add(subGrupoProduto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsSubGrupoProdutos, stmt, conn);

		return subGrupoProdutos;

	}

}
