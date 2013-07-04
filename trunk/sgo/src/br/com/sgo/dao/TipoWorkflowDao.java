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
import br.com.sgo.modelo.TipoWorkflow;

@Component
public class TipoWorkflowDao extends Dao<TipoWorkflow> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoWorkflow;

	private String sqlTipoWorkflow = "SELECT TIPOWORKFLOW.tipoworkflow_id, TIPOWORKFLOW.empresa_id, TIPOWORKFLOW.organizacao_id, TIPOWORKFLOW.nome FROM TIPOWORKFLOW (NOLOCK) ";

	public TipoWorkflowDao(Session session, ConnJDBC conexao) {
		super(session, TipoWorkflow.class);
		this.conexao = conexao;
	}

	public Collection<TipoWorkflow> buscaTodosTipoWorkflow() {

		String sql = sqlTipoWorkflow;

		this.conn = this.conexao.getConexao();

		Collection<TipoWorkflow> tiposWorkflow = new ArrayList<TipoWorkflow>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsTipoWorkflow = this.stmt.executeQuery();

			while (rsTipoWorkflow.next()) {

				TipoWorkflow tipoWorkflow = new TipoWorkflow();

				tipoWorkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoWorkflow.setNome(rsTipoWorkflow.getString("nome"));

				tiposWorkflow.add(tipoWorkflow);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);
		return tiposWorkflow;
	}

	public TipoWorkflow buscaTipoWorkflowPorEmpresaOrganizacaoTipoworflow(Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {

		String sql = sqlTipoWorkflow;

		if (empresa_id != null)
			sql += " WHERE TIPOWORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOWORKFLOW.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND (TIPOWORKFLOW.tipoworkflow_id = ?)";

		this.conn = this.conexao.getConexao();
		TipoWorkflow tipoworkflow = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);
			this.rsTipoWorkflow = this.stmt.executeQuery();
			while (rsTipoWorkflow.next()) {
				tipoworkflow = new TipoWorkflow();
				tipoworkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoworkflow.setNome(rsTipoWorkflow.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);
		return tipoworkflow;
	}
	
	public Collection<TipoWorkflow> buscaTipoWorkflowPorNome(String nome) {

		String sql = sqlTipoWorkflow;

		if (nome != null)
			sql += " WHERE TIPOWORKFLOW.nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<TipoWorkflow> tipoWorkflows = new ArrayList<TipoWorkflow>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoWorkflow = this.stmt.executeQuery();

			while (rsTipoWorkflow.next()) {

				TipoWorkflow tipoWorkflow = new TipoWorkflow();
				tipoWorkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoWorkflow.setNome(rsTipoWorkflow.getString("nome"));
				tipoWorkflows.add(tipoWorkflow);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);
		return tipoWorkflows;

	}

	public Collection<TipoWorkflow> buscaTiposWorkflowPorEmpresaOrganizacaoNome(Long empresa_id, Long organizacao_id, String nome) {
		
		String sql = sqlTipoWorkflow;
		
		if (empresa_id != null)
			sql += " WHERE TIPOWORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOWORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOWORKFLOW.nome like ?)";
		
		this.conn = this.conexao.getConexao();

		Collection<TipoWorkflow> tipoWorkflows = new ArrayList<TipoWorkflow>();
		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsTipoWorkflow = this.stmt.executeQuery();

			while (rsTipoWorkflow.next()) {
				TipoWorkflow tipoWorkflow = new TipoWorkflow();

				tipoWorkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoWorkflow.setNome(rsTipoWorkflow.getString("nome"));

				tipoWorkflows.add(tipoWorkflow);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);

		return tipoWorkflows;

	}
	
public TipoWorkflow buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(Long empresa_id, Long organizacao_id, String nome) {
		
		String sql = sqlTipoWorkflow;
		
		if (empresa_id != null)
			sql += " WHERE TIPOWORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOWORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOWORKFLOW.nome like ?)";
		
		this.conn = this.conexao.getConexao();

		TipoWorkflow tipoWorkflow = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,nome);
			
			this.rsTipoWorkflow = this.stmt.executeQuery();

			while (rsTipoWorkflow.next()) {

				tipoWorkflow = new TipoWorkflow();

				tipoWorkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoWorkflow.setNome(rsTipoWorkflow.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);

		return tipoWorkflow;

	}
	
	public TipoWorkflow buscaTipoWorkflowPorEmpresaOrganizacaoNome(Long empresa_id, Long organizacao_id, String nome) {
		
		String sql = sqlTipoWorkflow;
		
		if (empresa_id != null)
			sql += " WHERE TIPOWORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOWORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOWORKFLOW.nome like ?)";
		
		this.conn = this.conexao.getConexao();

		TipoWorkflow tipoWorkflow = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsTipoWorkflow = this.stmt.executeQuery();

			while (rsTipoWorkflow.next()) {

				tipoWorkflow = new TipoWorkflow();

				tipoWorkflow.setTipoWorkflow_id(rsTipoWorkflow.getLong("tipoworkflow_id"));
				tipoWorkflow.setNome(rsTipoWorkflow.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoWorkflow, stmt, conn);

		return tipoWorkflow;

	}

}