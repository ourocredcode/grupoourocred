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
import br.com.sgo.modelo.GrupoProduto;
import br.com.sgo.modelo.Organizacao;

@Component
public class GrupoProdutoDao extends Dao<GrupoProduto> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsGrupoProdutos;
	
	private final String sqlGrupoProduto = "SELECT GRUPOPRODUTO.grupoproduto_id, GRUPOPRODUTO.empresa_id, GRUPOPRODUTO.organizacao_id, GRUPOPRODUTO.nome FROM GRUPOPRODUTO (NOLOCK) ";
	
	private final String sqlGruposProduto = "SELECT GRUPOPRODUTO.grupoproduto_id, GRUPOPRODUTO.nome AS grupoproduto_nome, GRUPOPRODUTO.isactive, GRUPOPRODUTO.empresa_id "+
						", EMPRESA.nome AS empresa_nome, GRUPOPRODUTO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM (ORGANIZACAO (NOLOCK) INNER JOIN GRUPOPRODUTO (NOLOCK) ON ORGANIZACAO.organizacao_id = GRUPOPRODUTO.organizacao_id) "+
						" INNER JOIN EMPRESA (NOLOCK) ON GRUPOPRODUTO.empresa_id = EMPRESA.empresa_id ";
	
	public GrupoProdutoDao(Session session, ConnJDBC conexao) {

		super(session, GrupoProduto.class);
		this.conexao = conexao;

	}
	
	public Collection<GrupoProduto> buscaAllGruposProduto() {

		String sql = sqlGruposProduto;

		this.conn = this.conexao.getConexao();

		Collection<GrupoProduto> gruposProduto = new ArrayList<GrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsGrupoProdutos = this.stmt.executeQuery();

			while (rsGrupoProdutos.next()) {

				getGrupoProduto(gruposProduto);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsGrupoProdutos, stmt, conn);

		return gruposProduto;

	}
	
	public GrupoProduto buscaGrupoProdutoByNome(String nome) {

		String sql = sqlGrupoProduto;

		if (nome != null)
			sql += " WHERE GRUPOPRODUTO.nome = ? ";

		this.conn = this.conexao.getConexao();

		GrupoProduto grupoProduto = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setString(1, nome);

			this.rsGrupoProdutos = this.stmt.executeQuery();

			while (rsGrupoProdutos.next()) {

				grupoProduto = new GrupoProduto();

				grupoProduto.setGrupoProduto_id(rsGrupoProdutos.getLong("grupoproduto_id"));
				grupoProduto.setNome(rsGrupoProdutos.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsGrupoProdutos, stmt, conn);

		return grupoProduto;

	}

	public Collection<GrupoProduto> buscaGrupoProdutosByNome(String nome) {

		String sql = sqlGruposProduto;

		this.conn = this.conexao.getConexao();

		Collection<GrupoProduto> grupoProdutos = new ArrayList<GrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsGrupoProdutos = this.stmt.executeQuery();

			while (rsGrupoProdutos.next()) {

				getGrupoProduto(grupoProdutos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoProdutos, stmt, conn);

		return grupoProdutos;

	}

	private void getGrupoProduto(Collection<GrupoProduto> gruposProduto) throws SQLException {

		GrupoProduto grupoProduto = new GrupoProduto();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();

		empresa.setEmpresa_id(rsGrupoProdutos.getLong("empresa_id"));
		empresa.setNome(rsGrupoProdutos.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsGrupoProdutos.getLong("organizacao_id"));
		organizacao.setNome(rsGrupoProdutos.getString("organizacao_nome"));

		grupoProduto.setEmpresa(empresa);
		grupoProduto.setOrganizacao(organizacao);
		grupoProduto.setGrupoProduto_id(rsGrupoProdutos.getLong("grupoproduto_id"));
		grupoProduto.setNome(rsGrupoProdutos.getString("grupoproduto_nome"));

		gruposProduto.add(grupoProduto);

	}

}
