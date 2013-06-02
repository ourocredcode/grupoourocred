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
import br.com.sgo.modelo.Perfil;

@Component
public class PerfilDao extends Dao<Perfil> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPerfil;
	
	private final String sqlPerfil = "select PERFIL.empresa_id, PERFIL.organizacao_id, PERFIL.perfil_id, PERFIL.nome FROM PERFIL (NOLOCK) ";

	public PerfilDao(Session session, ConnJDBC conexao) {
		super(session, Perfil.class);
		this.conexao = conexao;
	}

	public Collection<Perfil> buscaAllPerfis(Long empresa_id, Long organizacao_id) {

		String sql = sqlPerfil;
		
		if (empresa_id != null)
			sql += " WHERE PERFIL.empresa_id = ? ";		
		if (organizacao_id != null)
			sql += " AND PERFIL.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsPerfil = this.stmt.executeQuery();

			while (rsPerfil.next()) {

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsPerfil.getLong("perfil_id"));
				perfil.setNome(rsPerfil.getString("nome"));
				perfis.add(perfil);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfil, stmt, conn);

		return perfis;

	}

	public Collection<Perfil> buscaPerfisToWorkflowEtapaPerfil() {

		String sql = "SELECT DISTINCT(WORKFLOWPERFILACESSO.perfil_id), PERFIL.nome AS perfil_nome "+
					" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN WORKFLOWPERFILACESSO (NOLOCK) "+
					" ON EMPRESA.empresa_id = WORKFLOWPERFILACESSO.empresa_id) ON ORGANIZACAO.organizacao_id = WORKFLOWPERFILACESSO.organizacao_id) "+ 
					" INNER JOIN PERFIL (NOLOCK) ON WORKFLOWPERFILACESSO.perfil_id = PERFIL.perfil_id) "+
					" INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWPERFILACESSO.workflow_id = WORKFLOW.workflow_id ";

		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsPerfil = this.stmt.executeQuery();

			while (rsPerfil.next()) {

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsPerfil.getLong("perfil_id"));
				perfil.setNome(rsPerfil.getString("perfil_nome"));
				perfis.add(perfil);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfil, stmt, conn);

		return perfis;

	}

	public Collection<Perfil> buscaPerfis(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlPerfil;
		
		if (empresa_id != null)
			sql += " WHERE PERFIL.empresa_id = ? ";		
		if (organizacao_id != null)
			sql += " AND PERFIL.organizacao_id = ?";
		if (nome != null)
			sql += " AND PERFIL.nome = ?";

		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsPerfil = this.stmt.executeQuery();

			while (rsPerfil.next()) {

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsPerfil.getLong("perfil_id"));
				perfil.setNome(rsPerfil.getString("nome"));
				perfis.add(perfil);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfil, stmt, conn);

		return perfis;

	}
	
	public Collection<Perfil> buscaPerfisToWorkflowTransicaoByWorkflow(Long empresa_id, Long organizacao_id, Long workflow_id) {

		String sql = "SELECT WORKFLOWPERFILACESSO.empresa_id, EMPRESA.nome, WORKFLOWPERFILACESSO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
					", WORKFLOWPERFILACESSO.perfil_id, PERFIL.nome AS perfil_nome, WORKFLOWPERFILACESSO.workflow_id, WORKFLOW.nome AS workflow_nome "+
					", WORKFLOWPERFILACESSO.isleituraescrita, WORKFLOWPERFILACESSO.isactive "+
					" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN WORKFLOWPERFILACESSO (NOLOCK) ON (WORKFLOWPERFILACESSO.empresa_id = EMPRESA.empresa_id) "+ 
					" AND (EMPRESA.empresa_id = WORKFLOWPERFILACESSO.empresa_id)) ON (WORKFLOWPERFILACESSO.organizacao_id = ORGANIZACAO.organizacao_id) "+
					" AND (ORGANIZACAO.organizacao_id = WORKFLOWPERFILACESSO.organizacao_id)) "+
					" INNER JOIN PERFIL (NOLOCK) ON WORKFLOWPERFILACESSO.perfil_id = PERFIL.perfil_id) "+ 
					" INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWPERFILACESSO.workflow_id = WORKFLOW.workflow_id " +
					" WHERE WORKFLOWPERFILACESSO.empresa_id = ? AND WORKFLOWPERFILACESSO.organizacao_id = ? AND WORKFLOWPERFILACESSO.workflow_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);

			this.rsPerfil = this.stmt.executeQuery();

			while (rsPerfil.next()) {

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsPerfil.getLong("perfil_id"));
				perfil.setNome(rsPerfil.getString("perfil_nome"));
				perfis.add(perfil);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfil, stmt, conn);

		return perfis;

	}
	

}
