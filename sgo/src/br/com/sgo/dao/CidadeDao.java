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

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCidade;
	
	private String sqlCidades = " SELECT CIDADE.cidade_id, CIDADE.nome, CIDADE.empresa_id, EMPRESA.nome, CIDADE.organizacao_id, "
			+ "	ORGANIZACAO.nome FROM (ORGANIZACAO INNER JOIN CIDADE ON ORGANIZACAO.organizacao_id = CIDADE.organizacao_id) "
			+ "	INNER JOIN EMPRESA ON CIDADE.empresa_id = EMPRESA.empresa_id ";

	public CidadeDao(Session session, ConnJDBC conexao) {
		super(session, Cidade.class);
		this.conexao = conexao;
	}

	public Cidade buscaPorNome(String nome) {

		String sql = sqlCidades;

		if (!nome.equals(""))
			sql += " WHERE CIDADE.nome like ? ";

		this.conn = this.conexao.getConexao();

		Cidade cidade = new Cidade();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, nome);

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