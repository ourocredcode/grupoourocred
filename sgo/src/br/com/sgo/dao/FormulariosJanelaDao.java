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
import br.com.sgo.modelo.FormulariosJanela;

@Component
public class FormulariosJanelaDao extends Dao<FormulariosJanela> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFormulariosJanela;

	public FormulariosJanelaDao(Session session, ConnJDBC conexao) {
		super(session, FormulariosJanela.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<FormulariosJanela> buscaFomulariosJanela(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select FORMULARIOSJANELA.formulariosjanela_id, FORMULARIOSJANELA.nome from FORMULARIOSJANELA (NOLOCK) "
				+ "	WHERE FORMULARIOSJANELA.empresa_id = ? AND FORMULARIOSJANELA.organizacao_id = ? AND FORMULARIOSJANELA.nome like ? ";
		this.conn = this.conexao.getConexao();

		Collection<FormulariosJanela> formulariosJanela = new ArrayList<FormulariosJanela>();
		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsFormulariosJanela = this.stmt.executeQuery();

			while (rsFormulariosJanela.next()) {
				FormulariosJanela formulario = new FormulariosJanela();

				formulario.setFormulariosjanela_id(rsFormulariosJanela
						.getLong("formulariosjanela_id"));
				formulario.setNome(rsFormulariosJanela.getString("nome"));

				formulariosJanela.add(formulario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsFormulariosJanela, stmt, conn);

		return formulariosJanela;

	}

}
