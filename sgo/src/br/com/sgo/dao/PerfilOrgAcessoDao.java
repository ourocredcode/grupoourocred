package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.PerfilJanelaAcesso;
import br.com.sgo.modelo.PerfilOrgAcesso;

@Component
public class PerfilOrgAcessoDao extends Dao<PerfilOrgAcesso>{

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;

	public PerfilOrgAcessoDao(Session session, ConnJDBC conexao) {
		
		super(session, PerfilOrgAcesso.class);
		this.session = session;
		this.conexao = conexao;
		
	}
	
	public void insert(PerfilOrgAcesso perfilOrgAcesso) throws SQLException {

		String sql = "INSERT INTO PERFILORGACESSO " +
				"	(perfil_id, " +				
				"	 empresa_id ," +
				"	 organizacao_id ," +
				"	 isactive) " +
				"    VALUES (?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {
			
			this.conn.setAutoCommit(false);
			this.stmt = conn.prepareStatement(sql);
	
			this.stmt.setLong(1,perfilOrgAcesso.getPerfil().getPerfil_id());			
			this.stmt.setLong(2,perfilOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(3,perfilOrgAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setBoolean(4,perfilOrgAcesso.getIsActive());

			this.stmt.executeUpdate();

			this.conn.commit();

		}  catch (SQLException e) {			
			
			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}
			this.conexao.closeConnection(stmt, conn);
			
	}
	
}
