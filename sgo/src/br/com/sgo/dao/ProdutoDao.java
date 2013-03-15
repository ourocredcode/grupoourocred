package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Produto;

@Component
public class ProdutoDao extends Dao<Produto> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsSubGrupoProdutos;

	public ProdutoDao(Session session , ConnJDBC conexao) {
		super(session, Produto.class);
		this.session = session;
		this.conexao =conexao;
	}

}
