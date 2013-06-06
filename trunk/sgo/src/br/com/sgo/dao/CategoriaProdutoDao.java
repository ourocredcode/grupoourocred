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

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCategoriaProdutos;

	public CategoriaProdutoDao(Session session, ConnJDBC conexao) {

		super(session, CategoriaProduto.class);
		this.conexao = conexao;

	}

	public Collection<CategoriaProduto> buscaCategoriaProdutos(String nome) {

		String sql = "select CATEGORIAPRODUTO.categoriaproduto_id, CATEGORIAPRODUTO.nome from CATEGORIAPRODUTO (NOLOCK) "
				+ "	WHERE CATEGORIAPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaProduto> categoriaProdutos = new ArrayList<CategoriaProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

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

	public Collection<CategoriaProduto> buscaAllCategoriaProduto() {

		String sql = "select CATEGORIAPRODUTO.categoriaproduto_id, CATEGORIAPRODUTO.nome from CATEGORIAPRODUTO (NOLOCK) ";

		this.conn = this.conexao.getConexao();

		Collection<CategoriaProduto> categoriasProduto = new ArrayList<CategoriaProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsCategoriaProdutos = this.stmt.executeQuery();
			
			while (rsCategoriaProdutos.next()) {
				
				CategoriaProduto categoriaProduto = new CategoriaProduto();

				categoriaProduto.setCategoriaProduto_id(rsCategoriaProdutos.getLong("categoriaproduto_id"));
				categoriaProduto.setNome(rsCategoriaProdutos.getString("nome"));

				categoriasProduto.add(categoriaProduto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCategoriaProdutos, stmt, conn);

		return categoriasProduto;

	}

}
