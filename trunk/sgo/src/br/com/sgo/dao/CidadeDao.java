package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Cidade;

@Component
public class CidadeDao extends Dao<Cidade> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCidade;
	
	public CidadeDao(Session session,ConnJDBC conexao) {
		super(session, Cidade.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Cidade buscaPorNome(String nome){

		String sql = "select CIDADE.cidade_id, CIDADE.nome from CIDADE (NOLOCK) WHERE CIDADE.nome like ?";

		this.conn = this.conexao.getConexao();
		Cidade cidade = new Cidade();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");			
			this.rsCidade = this.stmt.executeQuery();

			while (rsCidade.next()) {
				cidade.setCidade_id(rsCidade.getLong("cidade_id"));				
				cidade.setNome(rsCidade.getString("nome"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCidade, stmt, conn);

		return cidade;

	}

}