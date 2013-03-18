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
import br.com.sgo.modelo.Tabela;

@Component
public class TabelaDao extends Dao<Tabela> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTabelas;

	public TabelaDao(Session session , ConnJDBC conexao) {
		super(session, Tabela.class);
		this.session = session;
		this.conexao =conexao;
	}
	
	public Collection<Tabela> buscaTabelas(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select TABELA.tabela_id, TABELA.nome from TABELA (NOLOCK) WHERE TABELA.empresa_id = ? AND TABELA.organizacao_id = ? AND TABELA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Tabela> tabelas = new ArrayList<Tabela>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {
				Tabela tabela = new Tabela();

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));				
				tabela.setNome(rsTabelas.getString("nome"));

				tabelas.add(tabela);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTabelas, stmt, conn);

		return tabelas;

	}

}
