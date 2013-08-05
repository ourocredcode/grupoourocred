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
import br.com.sgo.modelo.Convenio;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class ConvenioDao extends Dao<Convenio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsConvenio;

	private final String sqlConvenio = "SELECT CONVENIO.convenio_id, CONVENIO.nome, CONVENIO.empresa_id, CONVENIO.organizacao_id FROM CONVENIO (NOLOCK) ";

	private final String sqlConvenios = "SELECT CONVENIO.empresa_id, EMPRESA.nome as empresa_nome, CONVENIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome" +
										", CONVENIO.convenio_id, CONVENIO.nome as convenio_nome, CONVENIO.isactive " +
										" FROM (CONVENIO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON CONVENIO.empresa_id = EMPRESA.empresa_id) " +
										" INNER JOIN ORGANIZACAO (NOLOCK) ON CONVENIO.organizacao_id = ORGANIZACAO.organizacao_id ";

	public ConvenioDao(Session session, ConnJDBC conexao) {
		super(session, Convenio.class);
		this.conexao = conexao;
	}

	public Collection<Convenio> buscaAllConvenio(Long empresa_id, Long organizacao_id) {

		String sql = sqlConvenios;
		
		if (empresa_id != null)
			sql += " WHERE CONVENIO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CONVENIO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Convenio> convenios = new ArrayList<Convenio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsConvenio = this.stmt.executeQuery();

			while (rsConvenio.next()) {

				getConvenio(convenios);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsConvenio, stmt, conn);

		return convenios;

	}

	public Collection<Convenio> buscaConvenioToFillComboByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlConvenio;
		
		if (empresa_id != null)
			sql += " WHERE CONVENIO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CONVENIO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Convenio> convenios = new ArrayList<Convenio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsConvenio = this.stmt.executeQuery();

			while (rsConvenio.next()) {

				Convenio convenio = new Convenio();
				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));
				convenios.add(convenio);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenios;
	}

	public Convenio buscaConvenioByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlConvenio;

		if (empresa_id != null)
			sql += " WHERE CONVENIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND CONVENIO.organizacao_id = ? ";
		if (nome != null)
			sql += " AND CONVENIO.nome = ? ";

		this.conn = this.conexao.getConexao();

		Convenio convenio = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsConvenio = this.stmt.executeQuery();

			while (rsConvenio.next()) {

				convenio = new Convenio();

				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenio;
	}
	
	public Convenio buscaConvenioByEmpOrgId(Long empresa_id, Long organizacao_id, Long convenio_id) {

		String sql = sqlConvenio;

		if (empresa_id != null)
			sql += " WHERE CONVENIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND CONVENIO.organizacao_id = ? ";
		if (convenio_id != null)
			sql += " AND CONVENIO.convenio_id = ? ";

		this.conn = this.conexao.getConexao();

		Convenio convenio = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, convenio_id);
			
			this.rsConvenio = this.stmt.executeQuery();

			while (rsConvenio.next()) {

				convenio = new Convenio();

				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenio;
	}


	public Collection<Convenio> buscaConveniosByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlConvenio;
		
		if (empresa_id != null)
			sql += " WHERE CONVENIO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CONVENIO.organizacao_id = ?";
		if (nome != null)
			sql += " AND CONVENIO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Convenio> convenios = new ArrayList<Convenio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsConvenio = this.stmt.executeQuery();

			while (rsConvenio.next()) {

				getConvenio(convenios);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenios;
	}

	private void getConvenio(Collection<Convenio> convenios)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Convenio convenio = new Convenio();

		empresa.setEmpresa_id(rsConvenio.getLong("empresa_id"));
		empresa.setNome(rsConvenio.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsConvenio.getLong("organizacao_id"));		
		organizacao.setNome(rsConvenio.getString("organizacao_nome"));
		
		convenio.setEmpresa(empresa);
		convenio.setOrganizacao(organizacao);
		convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
		convenio.setNome(rsConvenio.getString("convenio_nome"));
		convenio.setIsActive(rsConvenio.getBoolean("isactive"));

		convenios.add(convenio);

	}

}