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
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.PerfilOrgAcesso;

@Component
public class PerfilOrgAcessoDao extends Dao<PerfilOrgAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPerfilOrgAcesso;

	public PerfilOrgAcessoDao(Session session, ConnJDBC conexao) {

		super(session, PerfilOrgAcesso.class);
		this.conexao = conexao;

	}
	
	public Collection<PerfilOrgAcesso> buscaAllPerfilOrgAcessoByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = "SELECT PERFIL.perfil_id, PERFIL.nome AS perfil_nome, PERFIL.isactive, "+
			"PERFIL.empresa_id, EMPRESA.nome AS empresa_nome, PERFIL.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
			", PERFIL.supervisor_usuario_id, USUARIO.nome AS supervisor_usuario_nome "+
			" FROM (ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
			" INNER JOIN PERFIL (NOLOCK) ON EMPRESA.empresa_id = PERFIL.empresa_id) ON ORGANIZACAO.organizacao_id = PERFIL.organizacao_id) "+ 
			" LEFT JOIN USUARIO (NOLOCK) ON PERFIL.supervisor_usuario_id = USUARIO.usuario_id ";
		
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

	public void insert(PerfilOrgAcesso perfilOrgAcesso) throws SQLException {

		String sql = "INSERT INTO PERFILORGACESSO " + "	(perfil_id, "
				+ "	 empresa_id ," + "	 organizacao_id ," + "	 isactive) "
				+ "    VALUES (?,?,?,?)";

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
		
		perfilOrgAcesso.setEmpresa(empresa);
		perfilOrgAcesso.setOrganizacao(organizacao);
		perfilOrgAcesso.setPerfil(perfil);
		perfilOrgAcesso.setIsActive(rsPerfilOrgAcesso.getBoolean("isactive"));

		perfisOrgAcesso.add(perfilOrgAcesso);

	}


}
