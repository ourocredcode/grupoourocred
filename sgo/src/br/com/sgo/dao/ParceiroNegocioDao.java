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
import br.com.sgo.modelo.ParceiroNegocio;

@Component
public class ParceiroNegocioDao extends Dao<ParceiroNegocio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroNegocio;

	private static final String sqlParceiroNegocio = " SELECT PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.nome, "
			+ "PARCEIRONEGOCIO.empresa_id, EMPRESA.nome, PARCEIRONEGOCIO.organizacao_id, ORGANIZACAO.nome FROM ORGANIZACAO "
			+ "INNER JOIN (EMPRESA INNER JOIN PARCEIRONEGOCIO ON EMPRESA.empresa_id = PARCEIRONEGOCIO.empresa_id) "
			+ "ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id ";

	public ParceiroNegocioDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroNegocio.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroNegocio> buscaParceiroNegocio(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlParceiroNegocio;
		
		if (empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? ";
		if (nome != null)
			sql += " AND PARCEIRONEGOCIO.nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroNegocio> parceiros = new ArrayList<ParceiroNegocio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {
				ParceiroNegocio parceiro = new ParceiroNegocio();

				parceiro.setParceiroNegocio_id(rsParceiroNegocio.getLong("parceironegocio_id"));
				parceiro.setNome(rsParceiroNegocio.getString("nome"));

				parceiros.add(parceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroNegocio, stmt, conn);
		return parceiros;
	}

	public ParceiroNegocio buscaParceiroNegocioByDocumento(Long empresa_id,Long organizacao_id, String doc) {

		String sql = sqlParceiroNegocio;

		if (empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? AND (PARCEIRONEGOCIO.cpf like ? OR PARCEIRONEGOCIO.rg like ?)";

		this.conn = this.conexao.getConexao();

		ParceiroNegocio parceiro = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, doc);
			this.stmt.setString(4, doc);

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				parceiro = new ParceiroNegocio();

				parceiro.setParceiroNegocio_id(rsParceiroNegocio
						.getLong("parceironegocio_id"));
				parceiro.setNome(rsParceiroNegocio.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroNegocio, stmt, conn);
		return parceiro;

	}

	public ParceiroNegocio buscaParceiroNegocioById(Long parceiro_id) {

		String sql = sqlParceiroNegocio;

		if (parceiro_id != null)
			sql += " WHERE PARCEIRONEGOCIO.parceironegocio_id = ?";

		this.conn = this.conexao.getConexao();
		ParceiroNegocio parceiro = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceiro_id);

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				parceiro = new ParceiroNegocio();

				parceiro.setParceiroNegocio_id(rsParceiroNegocio.getLong("parceironegocio_id"));
				parceiro.setNome(rsParceiroNegocio.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroNegocio, stmt, conn);

		return parceiro;

	}

	public Collection<ParceiroNegocio> buscaParceiroNegocioByPerfil(Long empresa_id, Long organizacao_id, String perfil) {

		String sql = " SELECT PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.nome,  PARCEIRONEGOCIO.empresa_id, " +
				"			  EMPRESA.nome, PARCEIRONEGOCIO.organizacao_id, ORGANIZACAO.nome  FROM PARCEIRONEGOCIO  " +
				"				INNER JOIN USUARIO ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id " +
				"				INNER JOIN USUARIOPERFIL ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id " +
				"				INNER JOIN PERFIL ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id " +
				"				INNER JOIN EMPRESA ON EMPRESA.empresa_id = USUARIO.empresa_id " +
				"				INNER JOIN ORGANIZACAO ON ORGANIZACAO.organizacao_id = USUARIO.organizacao_id ";

		if (empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? ";
		if (perfil != null)
			sql += " AND PERFIL.nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroNegocio> parceiros = new ArrayList<ParceiroNegocio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + perfil + "%");

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {

				ParceiroNegocio parceiro = new ParceiroNegocio();

				parceiro.setParceiroNegocio_id(rsParceiroNegocio.getLong("parceironegocio_id"));
				parceiro.setNome(rsParceiroNegocio.getString("nome"));

				parceiros.add(parceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroNegocio, stmt, conn);
		return parceiros;
	}

}
