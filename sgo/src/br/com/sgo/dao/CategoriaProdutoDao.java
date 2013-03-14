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
import br.com.sgo.modelo.CategoriaProduto;

@Component
public class CategoriaProdutoDao extends Dao<CategoriaProduto> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCategoriaProdutos;

	public CategoriaProdutoDao(Session session , ConnJDBC conexao) {
		super(session, CategoriaProduto.class);
		this.session = session;
		this.conexao =conexao;
	}
	
	public Collection<CategoriaProduto> buscaCategoriaProdutos(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select CATEGORIAPRODUTO.categoriaproduto_id, CATEGORIAPRODUTO.nome from CATEGORIAPRODUTO (NOLOCK) " +
				"	WHERE CATEGORIAPRODUTO.empresa_id = ? AND CATEGORIAPRODUTO.organizacao_id = ? AND CATEGORIAPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaProduto> categoriaProdutos = new ArrayList<CategoriaProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsCategoriaProdutos = this.stmt.executeQuery();

			while (rsCategoriaProdutos.next()) {
				CategoriaProduto categoriaProduto = new CategoriaProduto();

				categoriaProduto.setCategoriaProduto_id(rsCategoriaProdutos.getLong("categoriaproduto_id"));				
				categoriaProduto.setNome(rsCategoriaProdutos.getString("nome"));

				categoriaProdutos.add(categoriaProduto);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCategoriaProdutos, stmt, conn);

		return categoriaProdutos;

	}
	
	public Collection<CategoriaProduto> buscaCategoriaProdutos(Long empresa_id, Long organizacao_id){

		String sql = "select CATEGORIAPRODUTO.categoriaproduto_id, CATEGORIAPRODUTO.nome from CATEGORIAPRODUTO (NOLOCK) " +
				"	WHERE CATEGORIAPRODUTO.empresa_id = ? AND CATEGORIAPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaProduto> categoriaProdutos = new ArrayList<CategoriaProduto>();

		try {
			
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);					
			this.rsCategoriaProdutos = this.stmt.executeQuery();

			while (rsCategoriaProdutos.next()) {
				CategoriaProduto categoriaProduto = new CategoriaProduto();
				
				categoriaProduto.setCategoriaProduto_id(rsCategoriaProdutos.getLong("CategoriaProduto_id"));				
				categoriaProduto.setNome(rsCategoriaProdutos.getString("nome"));

				categoriaProdutos.add(categoriaProduto);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCategoriaProdutos, stmt, conn);

		return categoriaProdutos;

	}

}
