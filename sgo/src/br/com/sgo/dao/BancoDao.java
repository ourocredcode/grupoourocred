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
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.ClassificacaoBanco;
import br.com.sgo.modelo.GrupoBanco;

@Component
public class BancoDao extends Dao<Banco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsBanco;

	private final String sqlBanco = "SELECT BANCO.banco_id, BANCO.nome, BANCO.iscomprado from BANCO (NOLOCK) ";

	private final String sqlBancos = "SELECT BANCO.banco_id, BANCO.iscomprado, BANCO.isactive, BANCO.nome AS banco_nome, BANCO.grupobanco_id "+
					", GRUPOBANCO.nome AS grupobanco_nome, BANCO.classificacaobanco_id, CLASSIFICACAOBANCO.nome AS classificacaobanco_nome "+
					" FROM (BANCO (NOLOCK) INNER JOIN GRUPOBANCO (NOLOCK) ON BANCO.grupobanco_id = GRUPOBANCO.grupobanco_id) "+
					" LEFT JOIN CLASSIFICACAOBANCO (NOLOCK) ON BANCO.classificacaobanco_id = CLASSIFICACAOBANCO.classificacaobanco_id ";

	public BancoDao(Session session, ConnJDBC conexao) {

		super(session, Banco.class);
		this.conexao = conexao;

	}

	public Collection<Banco> buscaAllBancos() {

		String sql = sqlBancos;

		this.conn = this.conexao.getConexao();

		Collection<Banco> bancos = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {

				getBanco(bancos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return bancos;

	}

	public Collection<Banco> buscaBancosToBancoProdutoByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = " SELECT DISTINCT(BANCOPRODUTO.banco_id), BANCO.nome "+
						" FROM ((BANCO (NOLOCK) INNER JOIN BANCOPRODUTO ON BANCO.banco_id = BANCOPRODUTO.banco_id) "+
						" INNER JOIN EMPRESA (NOLOCK) ON BANCOPRODUTO.empresa_id = EMPRESA.empresa_id) "+
						" INNER JOIN ORGANIZACAO (NOLOCK) ON BANCOPRODUTO.organizacao_id = ORGANIZACAO.organizacao_id "+
						" WHERE BANCOPRODUTO.empresa_id = ? AND BANCOPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Banco> bancos = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {

				Banco banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

				bancos.add(banco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return bancos;

	}

	public Collection<Banco> buscaBancosByNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = "select BANCO.banco_id, BANCO.nome from BANCO (NOLOCK) WHERE BANCO.empresa_id = ? AND BANCO.organizacao_id = ? AND BANCO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Banco> bancos = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {
				Banco banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

				bancos.add(banco);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return bancos;

	}
	
	public Banco buscaBancoByNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = "select BANCO.banco_id, BANCO.nome from BANCO (NOLOCK) WHERE BANCO.empresa_id = ? AND BANCO.organizacao_id = ? AND BANCO.nome like ?";

		this.conn = this.conexao.getConexao();

		Banco banco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,nome);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {
				
				banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return banco;

	}
	
	public Banco buscaBancoByEmpOrgGrupoClassificacaoNome(Long empresa_id, Long organizacao_id, Long grupoBanco_id, Long classificacaoBanco_id, String nome) {

		String sql = sqlBanco;
		
		if (empresa_id != null)
			sql += " WHERE BANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCO.organizacao_id = ?";
		if (grupoBanco_id != null)
			sql += " AND BANCO.grupobanco_id = ?";
		if (classificacaoBanco_id != null)
			sql += " AND BANCO.classificacaobanco_id = ?";
		if (nome != null)
			sql += " AND BANCO.nome = ?";

		this.conn = this.conexao.getConexao();

		Banco banco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, grupoBanco_id);
			this.stmt.setLong(4, classificacaoBanco_id);
			this.stmt.setString(5, nome);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {
				
				banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return banco;

	}

	public Banco buscaBancoById(Long banco_id) {

		String sql = "select BANCO.banco_id, BANCO.nome from BANCO (NOLOCK) WHERE BANCO.banco_id = ? ";

		this.conn = this.conexao.getConexao();

		Banco banco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, banco_id);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {
				
				banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return banco;

	}

	public Collection<Banco> buscaBancoCompradoByEmpOrg(Long empresa_id, Long organizacao_id){

		String sql = sqlBanco;

		if (empresa_id != null)
			sql += " WHERE BANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCO.organizacao_id = ? AND BANCO.iscomprado = 1";

		this.conn = this.conexao.getConexao();

		Collection<Banco> bancosComprado = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {

				Banco banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

				bancosComprado.add(banco);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return bancosComprado;

	}
	
	public Collection<Banco> buscaBancoByProcedimento(Long procedimento_id){
		
		String sql = "SELECT PROCEDIMENTOBANCO.banco_id, BANCO.nome FROM PROCEDIMENTOBANCO (NOLOCK) " +
				" INNER JOIN BANCO (NOLOCK) ON PROCEDIMENTOBANCO.banco_id = BANCO.banco_id ";

		if (procedimento_id != null)
			sql += " WHERE PROCEDIMENTOBANCO.procedimento_id = ?";
		
		this.conn = this.conexao.getConexao();

		
		Collection<Banco> bancos = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, procedimento_id);

			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {

				Banco banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));
				banco.setNome(rsBanco.getString("nome"));

				bancos.add(banco);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);
		return bancos;

	}

	private void getBanco(Collection<Banco> bancos) throws SQLException {

		Banco banco = new Banco();
		GrupoBanco grupoBanco = new GrupoBanco();
		ClassificacaoBanco classificacaoBanco = new ClassificacaoBanco();
		
		grupoBanco.setGrupoBanco_id(rsBanco.getLong("grupobanco_id"));
		grupoBanco.setNome(rsBanco.getString("grupobanco_nome"));
		
		classificacaoBanco.setClassificacaoBanco_id(rsBanco.getLong("classificacaobanco_id"));
		classificacaoBanco.setNome(rsBanco.getString("classificacaobanco_nome"));
		
		banco.setGrupoBanco(grupoBanco);
		banco.setClassificacaoBanco(classificacaoBanco);				
		banco.setBanco_id(rsBanco.getLong("banco_id"));
		banco.setNome(rsBanco.getString("banco_nome"));
		banco.setIsActive(rsBanco.getBoolean("isactive"));
		banco.setIsComprado(rsBanco.getBoolean("iscomprado"));

		bancos.add(banco);

	}

}