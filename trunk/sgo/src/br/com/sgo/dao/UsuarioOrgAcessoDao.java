package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.UsuarioOrgAcesso;

@Component
public class UsuarioOrgAcessoDao extends Dao<UsuarioOrgAcesso> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;

	public UsuarioOrgAcessoDao(Session session, ConnJDBC conexao) {
		super(session, UsuarioOrgAcesso.class);
		this.session = session;
		this.conexao = conexao;
	}

	public void insert(UsuarioOrgAcesso usuarioOrgAcesso) throws SQLException {

		String sql = "INSERT INTO USUARIOORGACESSO " + "	(usuario_id, "
				+ "	 empresa_id ," + "	 organizacao_id) "
				+ "    VALUES (?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, usuarioOrgAcesso.getUsuario().getUsuario_id());
			this.stmt.setLong(2, usuarioOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(3, usuarioOrgAcesso.getOrganizacao()
					.getOrganizacao_id());

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

}
