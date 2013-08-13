package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.PerfilOrgAcesso;

@Component
public class PerfilOrgAcessoDao extends Dao<PerfilOrgAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPerfilOrgAcesso;

	private final String sqlPerfilOrgsAcesso = "SELECT PERFILORGACESSO.empresa_id, EMPRESA.nome as empresa_nome, PERFILORGACESSO.organizacao_id "+
											", ORGANIZACAO.nome AS organizacao_nome, PERFILORGACESSO.perfil_id, PERFIL.nome AS perfil_nome "+
											", PERFIL.isactive as perfil_isactive, PERFILORGACESSO.isactive as perforg_isactive"+
											" FROM ((PERFILORGACESSO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON PERFILORGACESSO.empresa_id = EMPRESA.empresa_id) "+ 
											" INNER JOIN ORGANIZACAO (NOLOCK) ON PERFILORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) "+ 
											" INNER JOIN PERFIL (NOLOCK) ON PERFILORGACESSO.perfil_id = PERFIL.perfil_id ";
	
	public PerfilOrgAcessoDao(Session session, ConnJDBC conexao) {

		super(session, PerfilOrgAcesso.class);
		this.conexao = conexao;

	}
	
	public Collection<PerfilOrgAcesso> buscaAllPerfilOrgAcessoByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlPerfilOrgsAcesso;
		
		if (empresa_id != null)
			sql += " WHERE PERFIL.empresa_id = ? ";		
		if (organizacao_id != null)
			sql += " AND PERFIL.organizacao_id = ?";
			sql += " ORDER BY PERFIL.nome ";
		

		this.conn = this.conexao.getConexao();

		Collection<PerfilOrgAcesso> perfisOrgAcesso = new ArrayList<PerfilOrgAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsPerfilOrgAcesso= this.stmt.executeQuery();

			while (rsPerfilOrgAcesso.next()) {

				getPerfilOrgAcesso(perfisOrgAcesso);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfilOrgAcesso, stmt, conn);

		return perfisOrgAcesso;

	}
	
	public PerfilOrgAcesso buscaUsuarioOrgAcessoByEmpOrgPerfil(Long empresa_id, Long organizacao_id, Long perfil_id) {

		String sql = sqlPerfilOrgsAcesso;

		this.conn = this.conexao.getConexao();

		if (empresa_id != null)
			sql += " WHERE PERFILORGACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PERFILORGACESSO.organizacao_id = ?";
		if (perfil_id != null)
			sql += " AND PERFILORGACESSO.perfil_id = ?";

		PerfilOrgAcesso perfilOrgAcesso = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, perfil_id);

			this.rsPerfilOrgAcesso = this.stmt.executeQuery();

			while (rsPerfilOrgAcesso.next()) {

				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Perfil perfil = new Perfil();
				perfilOrgAcesso = new PerfilOrgAcesso();

				empresa.setEmpresa_id(rsPerfilOrgAcesso.getLong("empresa_id"));
				organizacao.setOrganizacao_id(rsPerfilOrgAcesso.getLong("organizacao_id"));
				perfil.setPerfil_id(rsPerfilOrgAcesso.getLong("perfil_id"));
				perfilOrgAcesso.setIsActive(rsPerfilOrgAcesso.getBoolean("perforg_isactive"));
				
				perfilOrgAcesso.setEmpresa(empresa);
				perfilOrgAcesso.setOrganizacao(organizacao);
				perfilOrgAcesso.setPerfil(perfil);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPerfilOrgAcesso, stmt, conn);
		return perfilOrgAcesso;
	}

	public void insert(PerfilOrgAcesso perfilOrgAcesso) throws SQLException {

		String sql = "INSERT INTO PERFILORGACESSO " + "	(perfil_id, empresa_id, organizacao_id, isactive) VALUES (?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, perfilOrgAcesso.getPerfil().getPerfil_id());
			this.stmt.setLong(2, perfilOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(3, perfilOrgAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setBoolean(4, perfilOrgAcesso.getIsActive());

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
	
	public void altera(PerfilOrgAcesso perfilOrgAcesso) throws SQLException {

		String sql = "UPDATE PERFILORGACESSO SET updated=? , updatedby=?, isactive=? "
				+ " WHERE PERFILORGACESSO.empresa_id=? AND PERFILORGACESSO.organizacao_id=? AND PERFILORGACESSO.perfil_id=? ";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setTimestamp(1, new Timestamp(perfilOrgAcesso.getUpdated().getTimeInMillis()));
			this.stmt.setLong(2, perfilOrgAcesso.getUpdatedBy().getUsuario_id());
			this.stmt.setBoolean(3, perfilOrgAcesso.getIsActive());
			this.stmt.setLong(4, perfilOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(5, perfilOrgAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(6, perfilOrgAcesso.getPerfil().getPerfil_id());

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
	private void getPerfilOrgAcesso(Collection<PerfilOrgAcesso> perfisOrgAcesso) throws SQLException {
		
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Perfil perfil = new Perfil();
		PerfilOrgAcesso perfilOrgAcesso = new PerfilOrgAcesso();

		empresa.setEmpresa_id(rsPerfilOrgAcesso.getLong("empresa_id"));
		empresa.setNome(rsPerfilOrgAcesso.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsPerfilOrgAcesso.getLong("organizacao_id"));
		organizacao.setNome(rsPerfilOrgAcesso.getString("organizacao_nome"));

		perfil.setPerfil_id(rsPerfilOrgAcesso.getLong("perfil_id"));
		perfil.setNome(rsPerfilOrgAcesso.getString("perfil_nome"));
		perfil.setIsActive(rsPerfilOrgAcesso.getBoolean("perfil_isactive"));
		
		perfilOrgAcesso.setEmpresa(empresa);
		perfilOrgAcesso.setOrganizacao(organizacao);
		perfilOrgAcesso.setPerfil(perfil);
		perfilOrgAcesso.setIsActive(rsPerfilOrgAcesso.getBoolean("perforg_isactive"));

		perfisOrgAcesso.add(perfilOrgAcesso);

	}


}
