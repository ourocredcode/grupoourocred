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
import br.com.sgo.modelo.ClassificacaoBanco;

@Component
public class ClassificacaoBancoDao extends Dao<ClassificacaoBanco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsClassificacaoBanco;

	private final String sqlClassificacaoBanco = "SELECT CLASSIFICACAOBANCO.classificacaobanco_id, CLASSIFICACAOBANCO.nome FROM CLASSIFICACAOBANCO (NOLOCK) ";
	
	public ClassificacaoBancoDao(Session session, ConnJDBC conexao) {

		super(session, ClassificacaoBanco.class);
		this.conexao = conexao;

	}

	public Collection<ClassificacaoBanco> buscaAllClassificacaoBanco() {

		String sql = sqlClassificacaoBanco;

		this.conn = this.conexao.getConexao();

		Collection<ClassificacaoBanco> classificacaoBancos = new ArrayList<ClassificacaoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsClassificacaoBanco = this.stmt.executeQuery();

			while (rsClassificacaoBanco.next()) {

				ClassificacaoBanco classificacaoBanco = new ClassificacaoBanco();

				classificacaoBanco.setClassificacaoBanco_id(rsClassificacaoBanco.getLong("classificacaobanco_id"));
				classificacaoBanco.setNome(rsClassificacaoBanco.getString("nome"));

				classificacaoBancos.add(classificacaoBanco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsClassificacaoBanco, stmt, conn);
		return classificacaoBancos;
	}

}