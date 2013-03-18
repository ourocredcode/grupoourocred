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

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPerfil;

	public PerfilDao(Session session, ConnJDBC conexao) {
		super(session, Perfil.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<Perfil> buscaPerfis(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select PERFIL.perfil_id, PERFIL.nome from PERFIL (NOLOCK) " +
				"	WHERE PERFIL.empresa_id = ? AND PERFIL.organizacao_id = ? AND PERFIL.nome like ? ";
		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();
		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" +  nome + "%");			
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

}
