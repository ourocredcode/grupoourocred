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
import br.com.sgo.modelo.TipoOrganizacao;

@Component
public class TipoOrganizacaoDao extends Dao<TipoOrganizacao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoOrganizacao;

	private final String sqlTipoOrganizacao = "SELECT TIPOORGANIZACAO.tipoorganizacao_id, TIPOORGANIZACAO.nome, TIPOORGANIZACAO.empresa_id"
			+ ", TIPOORGANIZACAO.organizacao_id FROM TIPOORGANIZACAO (NOLOCK)";

	public TipoOrganizacaoDao(Session session, ConnJDBC conexao) {
		super(session, TipoOrganizacao.class);
		this.conexao = conexao;
	}

	public Collection<TipoOrganizacao> buscaAllTipoOrganizacao() {
		
		String sql = sqlTipoOrganizacao;

		this.conn = this.conexao.getConexao();
		
		Collection<TipoOrganizacao> tiposOrganizacao = new ArrayList<TipoOrganizacao>();

		try {
		
			this.stmt = conn.prepareStatement(sql);

			this.rsTipoOrganizacao = this.stmt.executeQuery();
			
			while (rsTipoOrganizacao.next()) {

				TipoOrganizacao tipoOrganizacao = new TipoOrganizacao();
				tipoOrganizacao.setTipoOrganizacao_id(rsTipoOrganizacao.getLong("tipoorganizacao_id"));
				tipoOrganizacao.setNome(rsTipoOrganizacao.getString("nome"));
				
				tiposOrganizacao.add(tipoOrganizacao);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoOrganizacao, stmt, conn);
		return tiposOrganizacao;
	}

	public Collection<TipoOrganizacao> buscaTipoOrganizacaoToFillCombosByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlTipoOrganizacao;

		if (empresa_id != null)
			sql += " WHERE TIPOORGANIZACAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOORGANIZACAO.organizacao_id = ? AND TIPOORGANIZACAO.isactive = 1";

		this.conn = this.conexao.getConexao();
		
		Collection<TipoOrganizacao> tiposOrganizacao = new ArrayList<TipoOrganizacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsTipoOrganizacao = this.stmt.executeQuery();

			while (rsTipoOrganizacao.next()) {

				TipoOrganizacao tipoOrganizacao = new TipoOrganizacao();

				tipoOrganizacao.setTipoOrganizacao_id(rsTipoOrganizacao.getLong("tipoorganizacao_id"));
				tipoOrganizacao.setNome(rsTipoOrganizacao.getString("nome"));

				tiposOrganizacao.add(tipoOrganizacao);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoOrganizacao, stmt, conn);
		return tiposOrganizacao;
	}

	public Collection<TipoOrganizacao> buscaTipoOrganizacaoByNome(String nome) {

		String sql = sqlTipoOrganizacao;

		if (nome != null)
			sql += " WHERE TIPOORGANIZACAO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoOrganizacao> tiposOrganizacao = new ArrayList<TipoOrganizacao>();

		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoOrganizacao = this.stmt.executeQuery();

			TipoOrganizacao tipoOrganizacao = new TipoOrganizacao();

			while (rsTipoOrganizacao.next()) {

				tipoOrganizacao.setTipoOrganizacao_id(rsTipoOrganizacao.getLong("tipoorganizacao_id"));
				tipoOrganizacao.setNome(rsTipoOrganizacao.getString("nome"));

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoOrganizacao, stmt, conn);
		return tiposOrganizacao;
	}

	public TipoOrganizacao buscaTipoOrganizacaoByEmOrNo(Long empresa,Long organizacao, String nome) {

		String sql = sqlTipoOrganizacao;

		if (empresa != null)
			sql += " AND TIPOORGANIZACAO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND TIPOORGANIZACAO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOORGANIZACAO.nome like ?)";

		this.conn = this.conexao.getConexao();

		TipoOrganizacao tipoOrganizacao = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoOrganizacao = this.stmt.executeQuery();

			while (rsTipoOrganizacao.next()) {

				tipoOrganizacao = new TipoOrganizacao();
				tipoOrganizacao.setTipoOrganizacao_id(rsTipoOrganizacao.getLong("tipoorganizacao_id"));
				tipoOrganizacao.setNome(rsTipoOrganizacao.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoOrganizacao, stmt, conn);
		return tipoOrganizacao;
	}

}