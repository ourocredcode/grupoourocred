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
import br.com.sgo.modelo.CategoriaParceiro;

@Component
public class CategoriaParceiroDao extends Dao<CategoriaParceiro> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCategoriaParceiro;
	
	public CategoriaParceiroDao(Session session,ConnJDBC conexao) {
		super(session, CategoriaParceiro.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<CategoriaParceiro> buscaCategoriaParceiro(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select CATEGORIAPARCEIRO.categoriaparceiro_id, CATEGORIAPARCEIRO.nome from CATEGORIAPARCEIRO (NOLOCK) WHERE CATEGORIAPARCEIRO.empresa_id = ? AND CATEGORIAPARCEIRO.organizacao_id = ? AND CATEGORIAPARCEIRO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaParceiro> categoriasparceiro = new ArrayList<CategoriaParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsCategoriaParceiro = this.stmt.executeQuery();

			while (rsCategoriaParceiro.next()) {
				CategoriaParceiro categoriaparceiro = new CategoriaParceiro();

				categoriaparceiro.setCategoriaParceiro_id(rsCategoriaParceiro.getLong("categoriaparceiro_id"));				
				categoriaparceiro.setNome(rsCategoriaParceiro.getString("nome"));

				categoriasparceiro.add(categoriaparceiro);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCategoriaParceiro, stmt, conn);

		return categoriasparceiro;

	}

}