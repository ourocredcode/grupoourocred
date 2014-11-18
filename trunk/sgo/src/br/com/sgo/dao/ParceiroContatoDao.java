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
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.TipoContato;
import br.com.sgo.modelo.Usuario;

@Component
public class ParceiroContatoDao extends Dao<ParceiroContato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroContato;

	private String sqlParceiroContatoAll = " SELECT PARCEIROCONTATO.parceirocontato_id, PARCEIROCONTATO.empresa_id, PARCEIROCONTATO.organizacao_id,"
			+ " PARCEIROCONTATO.parceironegocio_id, PARCEIROCONTATO.tipocontato_id, PARCEIROCONTATO.nome FROM PARCEIROCONTATO (NOLOCK) ";

	private String sqlParceiroContato = "SELECT PARCEIROCONTATO.parceirocontato_id, PARCEIROCONTATO.nome as parceirocontato_nome, PARCEIROCONTATO.empresa_id, PARCEIROCONTATO.organizacao_id, "
			+ "PARCEIROCONTATO.parceironegocio_id, PARCEIROCONTATO.tipocontato_id, TIPOCONTATO.nome as tipocontato_nome, PARCEIROCONTATO.isactive , USER_UPDATED.nome as userupdated_nome	FROM (((PARCEIROCONTATO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) "
			+ "ON PARCEIROCONTATO.empresa_id = EMPRESA.empresa_id) INNER JOIN ORGANIZACAO (NOLOCK) ON PARCEIROCONTATO.organizacao_id = ORGANIZACAO.organizacao_id) "
			+ "INNER JOIN TIPOCONTATO (NOLOCK) ON PARCEIROCONTATO.tipocontato_id = TIPOCONTATO.tipocontato_id) "
			+ "INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROCONTATO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id "
			+ "LEFT JOIN USUARIO (NOLOCK) AS USER_UPDATED ON PARCEIROCONTATO.updatedby = USER_UPDATED.usuario_id  ";

	public ParceiroContatoDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroContato.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroContato> buscaParceiroContatos(Long parceiroNegocio_id) {

		String sql = sqlParceiroContato;

		if (parceiroNegocio_id != null)
			sql += " WHERE PARCEIRONEGOCIO.parceironegocio_id = ?  ";

		this.conn = this.conexao.getConexao();
		Collection<ParceiroContato> parceiroContatos = new ArrayList<ParceiroContato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceiroNegocio_id);
			this.rsParceiroContato = this.stmt.executeQuery();

			while (rsParceiroContato.next()) {

				ParceiroContato parceiroContato = new ParceiroContato();
				parceiroContato.setParceiroContato_id(rsParceiroContato.getLong("parceirocontato_id"));
				parceiroContato.setNome(rsParceiroContato.getString("parceirocontato_nome"));

				Usuario userupdated = new Usuario();
				userupdated.setNome(rsParceiroContato.getString("userupdated_nome"));
				parceiroContato.setUpdatedBy(userupdated);

				TipoContato tipoContato = new TipoContato();
				tipoContato.setTipoContato_id(rsParceiroContato.getLong("tipocontato_id"));
				tipoContato.setNome(rsParceiroContato.getString("tipocontato_nome"));
				parceiroContato.setTipoContato(tipoContato);
				parceiroContato.setIsActive( rsParceiroContato.getBoolean("isactive"));

				parceiroContatos.add(parceiroContato);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroContato, stmt, conn);
		return parceiroContatos;

	}

	public ParceiroContato buscaParceiroContatoByTipoContatoNome(Long empresa_id, Long organizacao_id, Long parceiro_id, Long tipocontato_id,String nome ) {

		String sql = sqlParceiroContatoAll + " WHERE ";
		if (empresa_id != null)
			sql += " PARCEIROCONTATO.empresa_id = ?  ";
		if (organizacao_id != null)
			sql += " AND PARCEIROCONTATO.organizacao_id = ?  ";
		if (parceiro_id != null)
			sql += " AND PARCEIROCONTATO.parceironegocio_id = ? ";
		if (tipocontato_id != null)
			sql += " AND PARCEIROCONTATO.tipocontato_id = ?  ";
		if (nome != null)
			sql += " AND PARCEIROCONTATO.nome like ? ";

		this.conn = this.conexao.getConexao();

		ParceiroContato parceiroCont = null;

		try {

			//System.out.println(sql);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceiro_id);
			this.stmt.setLong(4, tipocontato_id);
			this.stmt.setString(5, nome);

			this.rsParceiroContato = this.stmt.executeQuery();

			while (rsParceiroContato.next()) {

				parceiroCont = new ParceiroContato();
				parceiroCont.setParceiroContato_id(rsParceiroContato.getLong("parceirocontato_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroContato, stmt, conn);
		return parceiroCont;

	}

	public ParceiroContato buscaParceiroContatoAll() {

		this.conn = this.conexao.getConexao();

		ParceiroContato parceiroContato = null;

		try {
			this.stmt = conn.prepareStatement(sqlParceiroContatoAll);
			this.rsParceiroContato = this.stmt.executeQuery();
			while (rsParceiroContato.next()) {
				parceiroContato = new ParceiroContato();
				parceiroContato.setParceiroContato_id(rsParceiroContato
						.getLong("parceirocontato_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroContato, stmt, conn);
		return parceiroContato;
	}

}