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
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoSaque;

@Component
public class TipoSaqueDao extends Dao<TipoSaque> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoSaque;

	private final String sqlTipoSaque = "SELECT TIPOSAQUE.tiposaque_id, TIPOSAQUE.nome AS tiposaque_nome, TIPOSAQUE.empresa_id, EMPRESA.nome AS empresa_nome "+
						", TIPOSAQUE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN TIPOSAQUE (NOLOCK) ON EMPRESA.empresa_id = TIPOSAQUE.empresa_id) ON ORGANIZACAO.organizacao_id = TIPOSAQUE.organizacao_id ";

	public TipoSaqueDao(Session session, ConnJDBC conexao) {
		super(session, TipoSaque.class);
		this.conexao = conexao;
	}

	public Collection<TipoSaque> buscaAllTipoSaque() {

		String sql = sqlTipoSaque;

		this.conn = this.conexao.getConexao();

		Collection<TipoSaque> tiposSaque = new ArrayList<TipoSaque>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsTipoSaque = this.stmt.executeQuery();

			while (rsTipoSaque.next()) {

				TipoSaque tipoSaque = new TipoSaque();
				tipoSaque.setTipoSaque_id(rsTipoSaque.getLong("tipoSaque_id"));
				tipoSaque.setNome(rsTipoSaque.getString("tiposaque_nome"));
				tiposSaque.add(tipoSaque);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		this.conexao.closeConnection(rsTipoSaque, stmt, conn);
		return tiposSaque;
	}

	public Collection<TipoSaque> buscaTiposSaqueByNome(String nome) {

		String sql = sqlTipoSaque;

		if (nome != null)
			sql += " WHERE TIPOSAQUE.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoSaque> tiposSaque = new ArrayList<TipoSaque>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoSaque = this.stmt.executeQuery();

			while (rsTipoSaque.next()) {
				
				getTipoSaque(tiposSaque);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		this.conexao.closeConnection(rsTipoSaque, stmt, conn);
		return tiposSaque;
	}

	public Collection<TipoSaque> buscaTiposSaqueByNome(Long empresa, Long organizacao, String nome) {

		String sql = sqlTipoSaque;

		if (empresa != null)
			sql += " WHERE TIPOSAQUE.empresa_id = ?";
		if (organizacao != null)
			sql += " AND TIPOSAQUE.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOSAQUE.nome like ?)";

		this.conn = this.conexao.getConexao();

		Collection<TipoSaque> tiposSaque = new ArrayList<TipoSaque>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoSaque = this.stmt.executeQuery();

			TipoSaque tipoSaque = new TipoSaque();

			while (rsTipoSaque.next()) {

				tipoSaque.setTipoSaque_id(rsTipoSaque.getLong("tiposaque_id"));
				tipoSaque.setNome(rsTipoSaque.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		this.conexao.closeConnection(rsTipoSaque, stmt, conn);
		return tiposSaque;
	}

	public TipoSaque buscaTipoSaqueByEmOrgNome(Long empresa, Long organizacao, String nome) {

		String sql = sqlTipoSaque;

		if (empresa != null)
			sql += " WHERE TIPOSAQUE.empresa_id = ?";
		if (organizacao != null)
			sql += " AND TIPOSAQUE.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOSAQUE.nome like ?)";

		this.conn = this.conexao.getConexao();

		TipoSaque tipoSaque = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoSaque = this.stmt.executeQuery();

			while (rsTipoSaque.next()) {

				tipoSaque = new TipoSaque();
				tipoSaque.setTipoSaque_id(rsTipoSaque.getLong("tiposaque_id"));
				tipoSaque.setNome(rsTipoSaque.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoSaque, stmt, conn);
		return tipoSaque;

	}

	private void getTipoSaque(Collection<TipoSaque> tiposSaque) throws SQLException {

		TipoSaque tipoSaque = new TipoSaque();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();

		empresa.setEmpresa_id(rsTipoSaque.getLong("empresa_id"));
		empresa.setNome(rsTipoSaque.getString("nome"));

		organizacao.setOrganizacao_id(rsTipoSaque.getLong("organizacao_id"));
		organizacao.setNome(rsTipoSaque.getString("nome"));

		tipoSaque.setEmpresa(empresa);
		tipoSaque.setOrganizacao(organizacao);
		tipoSaque.setTipoSaque_id(rsTipoSaque.getLong("tiposaque_id"));
		tipoSaque.setNome(rsTipoSaque.getString("nome"));

		tiposSaque.add(tipoSaque);

	}

}