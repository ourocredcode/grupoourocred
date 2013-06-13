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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoTabela;

@Component
public class TipoTabelaDao extends Dao<TipoTabela> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTiposTabela;

	private final String sqlTipoTabela = "SELECT TIPOTABELA.tipotabela_id, TIPOTABELA.empresa_id, TIPOTABELA.organizacao_id" +
			", TIPOTABELA.isactive, TIPOTABELA.nome FROM TIPOTABELA (NOLOCK) ";
	
	private final String sqlTiposTabela = " SELECT TIPOTABELA.empresa_id, EMPRESA.nome AS empresa_nome, TIPOTABELA.organizacao_id "+
				", ORGANIZACAO.nome AS organizacao_nome, TIPOTABELA.tipotabela_id, TIPOTABELA.nome AS tipotabela_nome, TIPOTABELA.isactive "+
				" FROM (TIPOTABELA (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON TIPOTABELA.empresa_id = EMPRESA.empresa_id) "+
				" INNER JOIN ORGANIZACAO (NOLOCK) ON TIPOTABELA.organizacao_id = ORGANIZACAO.organizacao_id ";

	public TipoTabelaDao(Session session, ConnJDBC conexao) {

		super(session, TipoTabela.class);		
		this.conexao = conexao;		

	}
	
	public Collection<TipoTabela> buscaAllTipoTabela(Long empresa_id, Long organizacao_id) {

		String sql = sqlTiposTabela;
		
		if (empresa_id != null)
			sql += " WHERE TIPOTABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOTABELA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoTabela> tiposTabela = new ArrayList<TipoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {
				
				getTipoTabela(tiposTabela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);

		return tiposTabela;

	}

	public TipoTabela buscaTipoTabelaByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlTipoTabela;

		if (empresa_id != null)
			sql += " WHERE TIPOTABELA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND TIPOTABELA.organizacao_id = ? ";
		if (nome != null)
			sql += " AND TIPOTABELA.nome = ? ";

		this.conn = this.conexao.getConexao();

		TipoTabela tipoTabela = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {

				tipoTabela = new TipoTabela();

				tipoTabela.setTipoTabela_id(rsTiposTabela.getLong("tipotabela_id"));
				tipoTabela.setNome(rsTiposTabela.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);
		return tipoTabela;
	}

	public Collection<TipoTabela> buscaTiposTabela(Long empresa_id, Long organizacao_id, String nome) {

		String sql = "select TIPOTABELA.tipotabela_id, TIPOTABELA.nome from TIPOTABELA (NOLOCK) "
				+ "	WHERE TIPOTABELA.empresa_id = ? AND TIPOTABELA.organizacao_id = ? AND TIPOTABELA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoTabela> tiposTabela = new ArrayList<TipoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {

				TipoTabela tipoTabela = new TipoTabela();

				tipoTabela.setTipoTabela_id(rsTiposTabela.getLong("tipotabela_id"));
				tipoTabela.setNome(rsTiposTabela.getString("nome"));

				tiposTabela.add(tipoTabela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);

		return tiposTabela;

	}

	public Collection<TipoTabela> buscaTiposTabela(Long empresa_id, Long organizacao_id) {

		String sql = " select TIPOTABELA.tipotabela_id , TIPOTABELA.nome from TIPOTABELA (NOLOCK) "
				+ "	WHERE TIPOTABELA.empresa_id = ? AND TIPOTABELA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoTabela> tiposTabela = new ArrayList<TipoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {

				TipoTabela tipoTabela = new TipoTabela();

				tipoTabela.setTipoTabela_id(rsTiposTabela.getLong("tipotabela_id"));
				tipoTabela.setNome(rsTiposTabela.getString("nome"));

				tiposTabela.add(tipoTabela);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);

		return tiposTabela;

	}

	private void getTipoTabela(Collection<TipoTabela> tiposTabela)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		TipoTabela tipoTabela = new TipoTabela();

		empresa.setEmpresa_id(rsTiposTabela.getLong("empresa_id"));
		empresa.setNome(rsTiposTabela.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsTiposTabela.getLong("organizacao_id"));		
		organizacao.setNome(rsTiposTabela.getString("organizacao_nome"));
		
		tipoTabela.setEmpresa(empresa);
		tipoTabela.setOrganizacao(organizacao);
		tipoTabela.setTipoTabela_id(rsTiposTabela.getLong("tipotabela_id"));
		tipoTabela.setNome(rsTiposTabela.getString("tipotabela_nome"));
		tipoTabela.setIsActive(rsTiposTabela.getBoolean("isactive"));

		tiposTabela.add(tipoTabela);

	}

}
