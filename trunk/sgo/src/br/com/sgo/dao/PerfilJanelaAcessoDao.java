package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.PerfilJanelaAcesso;

@Component
public class PerfilJanelaAcessoDao extends Dao<PerfilJanelaAcesso>{

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;

	public PerfilJanelaAcessoDao(Session session, ConnJDBC conexao) {
		
		super(session, PerfilJanelaAcesso.class);
		this.session = session;
		this.conexao = conexao;
		
	}
	
	public void insert(PerfilJanelaAcesso perfilJanelaAcesso) throws SQLException {

		String sql = "INSERT INTO PERFILJANELAACESSO " +
				"	(perfil_id, " +
				"	 janela_id," +
				"	 empresa_id ," +
				"	 organizacao_id ," +
				"	 isactive) " +
				"    VALUES (?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {
			
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,perfilJanelaAcesso.getPerfil().getPerfil_id());			
			this.stmt.setLong(2,perfilJanelaAcesso.getJanela().getJanela_id());
			this.stmt.setLong(3,perfilJanelaAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(4,perfilJanelaAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setBoolean(5,perfilJanelaAcesso.getIsActive());

			this.stmt.executeUpdate();

			this.conn.commit();

		}  catch (SQLException e) {

			this.conn.rollback();

			throw e;

		}	
			this.conexao.closeConnection(stmt, conn);
			
		}	

}
