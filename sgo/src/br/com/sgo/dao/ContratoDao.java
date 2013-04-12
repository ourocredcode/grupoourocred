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
import br.com.sgo.modelo.Contrato;

@Component
public class ContratoDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContrato;
	private static final String sqlContrato = "SELECT PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.nome, "
			+ "PARCEIRONEGOCIO.empresa_id, EMPRESA.nome, PARCEIRONEGOCIO.organizacao_id, ORGANIZACAO.nome FROM ORGANIZACAO "
			+ "INNER JOIN (EMPRESA INNER JOIN PARCEIRONEGOCIO ON EMPRESA.empresa_id = PARCEIRONEGOCIO.empresa_id) "
			+ "ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id";

	public ContratoDao(Session session, ConnJDBC conexao) {
		super(session, Contrato.class);
		this.conexao = conexao;
	}

	public Collection<Contrato> buscaContrato(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = sqlContrato;

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				Contrato parceiro = new Contrato();

				parceiro.setContrato_id(rsContrato
						.getLong("parceironegocio_id"));
				parceiro.setNome(rsContrato.getString("nome"));

				contratos.add(parceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

	public Contrato buscaContratoDocumento(Long empresa_id,
			Long organizacao_id, String doc) {

		String sql = sqlContrato;

		if (empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? AND (PARCEIRONEGOCIO.cpf like ? OR PARCEIRONEGOCIO.rg like ?)";

		this.conn = this.conexao.getConexao();
		Contrato parceiro = new Contrato();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + doc + "%");
			this.stmt.setString(4, "%" + doc + "%");

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				parceiro.setContrato_id(rsContrato
						.getLong("parceironegocio_id"));
				parceiro.setNome(rsContrato.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return parceiro;
	}

}
