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
import br.com.sgo.modelo.GrupoProduto;

@Component
public class GrupoProdutoDao extends Dao<GrupoProduto> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsGrupoProdutos;

	public GrupoProdutoDao(Session session , ConnJDBC conexao) {
		super(session, GrupoProduto.class);
		this.session = session;
		this.conexao =conexao;
	}
	
	public Collection<GrupoProduto> buscaGrupoProdutos(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select GRUPOPRODUTO.grupoproduto_id, GRUPOPRODUTO.nome from GRUPOPRODUTO (NOLOCK) " +
				"	WHERE GRUPOPRODUTO.empresa_id = ? AND GRUPOPRODUTO.organizacao_id = ? AND GRUPOPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<GrupoProduto> grupoProdutos = new ArrayList<GrupoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsGrupoProdutos = this.stmt.executeQuery();

			while (rsGrupoProdutos.next()) {
				GrupoProduto grupoProduto = new GrupoProduto();

				grupoProduto.setGrupoProduto_id(rsGrupoProdutos.getLong("grupoproduto_id"));				
				grupoProduto.setNome(rsGrupoProdutos.getString("nome"));

				grupoProdutos.add(grupoProduto);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoProdutos, stmt, conn);

		return grupoProdutos;

	}
	
	public Collection<GrupoProduto> buscaGrupoProdutos(Long empresa_id, Long organizacao_id){

		String sql = "select GRUPOPRODUTO.grupoproduto_id, GRUPOPRODUTO.nome from GRUPOPRODUTO (NOLOCK) " +
				"	WHERE GRUPOPRODUTO.empresa_id = ? AND GRUPOPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<GrupoProduto> grupoProdutos = new ArrayList<GrupoProduto>();

		try {
			
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);					
			this.rsGrupoProdutos = this.stmt.executeQuery();

			while (rsGrupoProdutos.next()) {
				GrupoProduto grupoProduto = new GrupoProduto();
				
				grupoProduto.setGrupoProduto_id(rsGrupoProdutos.getLong("grupoproduto_id"));				
				grupoProduto.setNome(rsGrupoProdutos.getString("nome"));

				grupoProdutos.add(grupoProduto);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoProdutos, stmt, conn);

		return grupoProdutos;

	}

}
