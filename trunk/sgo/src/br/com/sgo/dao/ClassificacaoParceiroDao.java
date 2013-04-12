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
import br.com.sgo.modelo.ClassificacaoParceiro;

@Component
public class ClassificacaoParceiroDao extends Dao<ClassificacaoParceiro> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsClassificacaoParceiro;

	public ClassificacaoParceiroDao(Session session, ConnJDBC conexao) {
		super(session, ClassificacaoParceiro.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<ClassificacaoParceiro> buscaClassificacaoParceiro(
			Long empresa_id, Long organizacao_id, String nome) {

		String sql = "select CLASSIFICACAOPARCEIRO.classificacaoparceiro_id, CLASSIFICACAOPARCEIRO.nome from CLASSIFICACAOPARCEIRO (NOLOCK) WHERE CLASSIFICACAOPARCEIRO.empresa_id = ? AND CLASSIFICACAOPARCEIRO.organizacao_id = ? AND CLASSIFICACAOPARCEIRO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<ClassificacaoParceiro> classificacoesparceiro = new ArrayList<ClassificacaoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsClassificacaoParceiro = this.stmt.executeQuery();

			while (rsClassificacaoParceiro.next()) {
				ClassificacaoParceiro classificacaoparceiro = new ClassificacaoParceiro();

				classificacaoparceiro
						.setClassificacaoParceiro_id(rsClassificacaoParceiro
								.getLong("classificacaoparceiro_id"));
				classificacaoparceiro.setNome(rsClassificacaoParceiro
						.getString("nome"));

				classificacoesparceiro.add(classificacaoparceiro);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsClassificacaoParceiro, stmt, conn);

		return classificacoesparceiro;

	}

}