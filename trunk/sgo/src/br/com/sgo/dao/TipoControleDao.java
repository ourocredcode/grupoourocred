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
import br.com.sgo.modelo.TipoControle;

@Component
public class TipoControleDao extends Dao<TipoControle> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoControles;

	private final String sqlTipoControle = "SELECT TIPOCONTROLE.tipocontrole_id, TIPOCONTROLE.empresa_id, TIPOCONTROLE.organizacao_id, TIPOCONTROLE.nome as tipocontrole_nome, TIPOCONTROLE.isactive FROM TIPOCONTROLE (NOLOCK) ";
	
	private final String sqlTipoControles = " SELECT TIPOCONTROLE.empresa_id, EMPRESA.nome AS empresa_nome, TIPOCONTROLE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome " +
				", TIPOCONTROLE.nome, TIPOCONTROLE.tipocontrole_id, TIPOCONTROLE.nome AS tipocontrole_nome, TIPOCONTROLE.isactive "+
				" FROM (TIPOCONTROLE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON TIPOCONTROLE.empresa_id = EMPRESA.empresa_id) INNER JOIN ORGANIZACAO (NOLOCK) ON TIPOCONTROLE.organizacao_id = ORGANIZACAO.organizacao_id ";

	public TipoControleDao(Session session, ConnJDBC conexao) {

		super(session, TipoControle.class);		
		this.conexao = conexao;		

	}
	
	public Collection<TipoControle> buscaAllTipoControleByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlTipoControles;
		
		if (empresa_id != null)
			sql += " WHERE TIPOCONTROLE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOCONTROLE.organizacao_id = ? ORDER BY TIPOCONTROLE.nome";

		this.conn = this.conexao.getConexao();

		Collection<TipoControle> tiposControle = new ArrayList<TipoControle>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsTipoControles = this.stmt.executeQuery();

			while (rsTipoControles.next()) {

				getTipoControle(tiposControle);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoControles, stmt, conn);

		return tiposControle;

	}

	public Collection<TipoControle> buscaTiposControleByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlTipoControles;
		
		if (empresa_id != null)
			sql += " WHERE TIPOCONTROLE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOCONTROLE.organizacao_id = ?";
		if (nome != null)
			sql += " AND TIPOCONTROLE.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoControle> tiposControle = new ArrayList<TipoControle>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoControles = this.stmt.executeQuery();

			while (rsTipoControles.next()) {

				getTipoControle(tiposControle);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoControles, stmt, conn);

		return tiposControle;

	}

	public TipoControle buscaTipoControleByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlTipoControle;

		if (empresa_id != null)
			sql += " WHERE TIPOCONTROLE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND TIPOCONTROLE.organizacao_id = ? ";
		if (nome != null)
			sql += " AND TIPOCONTROLE.nome = ? ";

		this.conn = this.conexao.getConexao();

		TipoControle tipoControle = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsTipoControles = this.stmt.executeQuery();

			while (rsTipoControles.next()) {

				tipoControle = new TipoControle();

				tipoControle.setTipoControle_id(rsTipoControles.getLong("tipocontrole_id"));
				tipoControle.setNome(rsTipoControles.getString("tipocontrole_nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoControles, stmt, conn);
		return tipoControle;
	}

	private void getTipoControle(Collection<TipoControle> tiposControle)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		TipoControle tipoControle = new TipoControle();

		empresa.setEmpresa_id(rsTipoControles.getLong("empresa_id"));
		empresa.setNome(rsTipoControles.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsTipoControles.getLong("organizacao_id"));		
		organizacao.setNome(rsTipoControles.getString("organizacao_nome"));
		
		tipoControle.setEmpresa(empresa);
		tipoControle.setOrganizacao(organizacao);
		tipoControle.setTipoControle_id(rsTipoControles.getLong("tipocontrole_id"));
		tipoControle.setNome(rsTipoControles.getString("tipocontrole_nome"));
		tipoControle.setIsActive(rsTipoControles.getBoolean("isactive"));

		tiposControle.add(tipoControle);

	}

}