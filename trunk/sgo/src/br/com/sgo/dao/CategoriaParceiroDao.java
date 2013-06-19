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
import br.com.sgo.modelo.CategoriaParceiro;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class CategoriaParceiroDao extends Dao<CategoriaParceiro> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCategoriaParceiro;
	
	private final String sqlCategoriaParceiro = "select CATEGORIAPARCEIRO.categoriaparceiro_id, CATEGORIAPARCEIRO.nome from CATEGORIAPARCEIRO (NOLOCK) ";
	
	private final String sqlCategoriasParceiro = "SELECT CATEGORIAPARCEIRO.empresa_id, EMPRESA.nome as empresa_nome, CATEGORIAPARCEIRO.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
									", CATEGORIAPARCEIRO.categoriaparceiro_id, CATEGORIAPARCEIRO.nome as categoriaparceiro_nome, CATEGORIAPARCEIRO.isactive "+
									" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN CATEGORIAPARCEIRO (NOLOCK) ON EMPRESA.empresa_id = CATEGORIAPARCEIRO.empresa_id) "+
									" ON ORGANIZACAO.organizacao_id = CATEGORIAPARCEIRO.organizacao_id ";

	public CategoriaParceiroDao(Session session, ConnJDBC conexao) {

		super(session, CategoriaParceiro.class);
		this.conexao = conexao;

	}

	public Collection<CategoriaParceiro> buscaAllCategoriaParceiroByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlCategoriasParceiro;

		if (empresa_id != null)
			sql += " WHERE CATEGORIAPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CATEGORIAPARCEIRO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaParceiro> categoriasParceiro = new ArrayList<CategoriaParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsCategoriaParceiro = this.stmt.executeQuery();

			while (rsCategoriaParceiro.next()) {

				getCategoriaParceiro(categoriasParceiro);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCategoriaParceiro, stmt, conn);
		return categoriasParceiro;
	}

	public Collection<CategoriaParceiro> buscaCategoriasParceiroByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlCategoriasParceiro;

		if (empresa_id != null)
			sql += " WHERE CATEGORIAPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CATEGORIAPARCEIRO.organizacao_id = ?";
		if (nome != null)
			sql += " AND CATEGORIAPARCEIRO.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<CategoriaParceiro> categoriasparceiro = new ArrayList<CategoriaParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsCategoriaParceiro = this.stmt.executeQuery();

			while (rsCategoriaParceiro.next()) {

				CategoriaParceiro categoriaparceiro = new CategoriaParceiro();

				categoriaparceiro.setCategoriaParceiro_id(rsCategoriaParceiro.getLong("categoriaparceiro_id"));
				categoriaparceiro.setNome(rsCategoriaParceiro.getString("nome"));

				categoriasparceiro.add(categoriaparceiro);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCategoriaParceiro, stmt, conn);
		return categoriasparceiro;
	}
	
	public CategoriaParceiro buscaCategoriaParceiroByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlCategoriaParceiro;

		if (empresa_id != null)
			sql += " WHERE CATEGORIAPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CATEGORIAPARCEIRO.organizacao_id = ?";
		if (nome != null)
			sql += " AND CATEGORIAPARCEIRO.nome = ? ";

		this.conn = this.conexao.getConexao();

		CategoriaParceiro categoriaParceiro = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsCategoriaParceiro = this.stmt.executeQuery();

			while (rsCategoriaParceiro.next()) {

				categoriaParceiro = new CategoriaParceiro();
				categoriaParceiro.setCategoriaParceiro_id(rsCategoriaParceiro.getLong("categoriaparceiro_id"));
				categoriaParceiro.setNome(rsCategoriaParceiro.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCategoriaParceiro, stmt, conn);
		return categoriaParceiro;
	}

	private void getCategoriaParceiro(Collection<CategoriaParceiro> categoriasParceiro)throws SQLException {

		CategoriaParceiro categoriaParceiro = new CategoriaParceiro();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao(); 
		
		empresa.setEmpresa_id(rsCategoriaParceiro.getLong("empresa_id"));
		empresa.setNome(rsCategoriaParceiro.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsCategoriaParceiro.getLong("organizacao_id"));
		organizacao.setNome(rsCategoriaParceiro.getString("organizacao_nome"));				
		
		categoriaParceiro.setEmpresa(empresa);
		categoriaParceiro.setOrganizacao(organizacao);
		
		categoriaParceiro.setCategoriaParceiro_id(rsCategoriaParceiro.getLong("categoriaparceiro_id"));
		categoriaParceiro.setNome(rsCategoriaParceiro.getString("categoriaparceiro_nome"));
		categoriaParceiro.setIsActive(rsCategoriaParceiro.getBoolean("isactive"));

		categoriasParceiro.add(categoriaParceiro);

	}

}