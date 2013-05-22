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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoBanco;
import br.com.sgo.modelo.ProcedimentoConferencia;

@Component
public class ProcedimentoBancoDao extends Dao<ProcedimentoBanco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProcedimentoBanco;

	private final String sqlProcedimentoBanco = "SELECT PROCEDIMENTOBANCO.empresa_id, EMPRESA.nome as empresa_nome, PROCEDIMENTOBANCO.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
									", PROCEDIMENTOBANCO.procedimento_id, PROCEDIMENTOCONFERENCIA.nome as procedimentoconferencia_nome, PROCEDIMENTOBANCO.banco_id, BANCO.nome as banco_nome "+
									" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
									" INNER JOIN PROCEDIMENTOBANCO (NOLOCK) ON EMPRESA.empresa_id = PROCEDIMENTOBANCO.empresa_id) ON ORGANIZACAO.organizacao_id = PROCEDIMENTOBANCO.organizacao_id) "+ 
									" INNER JOIN PROCEDIMENTOCONFERENCIA (NOLOCK) ON PROCEDIMENTOBANCO.procedimento_id = PROCEDIMENTOCONFERENCIA.procedimentoconferencia_id) "+
									" INNER JOIN BANCO (NOLOCK) ON PROCEDIMENTOBANCO.banco_id = BANCO.banco_id ";

	public ProcedimentoBancoDao(Session session, ConnJDBC conexao) {
		super(session, ProcedimentoBanco.class);
		this.conexao = conexao;
	}

	public Collection<ProcedimentoBanco> buscaAllProcedimentoBanco(Long empresa_id, Long organizacao_id) {

		String sql = sqlProcedimentoBanco;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOBANCO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoBanco> procedimentosBanco = new ArrayList<ProcedimentoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsProcedimentoBanco = this.stmt.executeQuery();

			while (rsProcedimentoBanco.next()) {

				getProcedimentoBanco(procedimentosBanco);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoBanco, stmt, conn);
		return procedimentosBanco;

	}

	public Collection<ProcedimentoBanco> buscaProcedimentosBanco(Long procedimento_id, Long banco_id) {

		String sql = sqlProcedimentoBanco;
		
		if (procedimento_id != null)
			sql += " WHERE PROCEDIMENTOBANCO.procedimento_id = ?";
		if (banco_id != null)
			sql += " AND (PROCEDIMENTOBANCO.banco_id = ?)";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoBanco> procedimentosBanco = new ArrayList<ProcedimentoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, procedimento_id);		
			this.stmt.setLong(3, banco_id);

			this.rsProcedimentoBanco = this.stmt.executeQuery();

			while (rsProcedimentoBanco.next()) {

				Banco banco = new Banco();
				banco.getBanco_id();

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoBanco, stmt, conn);
		return procedimentosBanco;

	}

	public ProcedimentoBanco buscaProcedimentoBancoByEmpOrgProcedimentoBanco(Long empresa_id, Long organizacao_id, Long procedimento_id, Long banco_id) {

		String sql = sqlProcedimentoBanco;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOBANCO.organizacao_id = ?";		
		if (procedimento_id != null)
			sql += " AND PROCEDIMENTOBANCO.procedimento_id = ?";
		if (banco_id != null)
			sql += " AND (PROCEDIMENTOBANCO.banco_id = ?)";

		this.conn = this.conexao.getConexao();

		ProcedimentoBanco procedimentoBanco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, procedimento_id);		
			this.stmt.setLong(4, banco_id);

			this.rsProcedimentoBanco = this.stmt.executeQuery();

			while (rsProcedimentoBanco.next()) {

				procedimentoBanco = new ProcedimentoBanco();

				Banco banco = new Banco();
				banco.setBanco_id(rsProcedimentoBanco.getLong("banco_id"));
				banco.setNome(rsProcedimentoBanco.getString("banco_nome"));

				procedimentoBanco.setBanco(banco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoBanco, stmt, conn);
		return procedimentoBanco;
	}

	public ProcedimentoBanco buscaProcedimentoBancoByEmpOrgProcedimento(Long empresa_id, Long organizacao_id, Long procedimento_id) {

		String sql = sqlProcedimentoBanco;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOBANCO.organizacao_id = ?";		
		if (procedimento_id != null)
			sql += " AND PROCEDIMENTOBANCO.procedimento_id = ?";
		
		this.conn = this.conexao.getConexao();

		ProcedimentoBanco procedimentoBanco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, procedimento_id);		

			this.rsProcedimentoBanco = this.stmt.executeQuery();

			while (rsProcedimentoBanco.next()) {

				procedimentoBanco = new ProcedimentoBanco();

				Banco banco = new Banco();
				banco.setBanco_id(rsProcedimentoBanco.getLong("banco_id"));
				banco.setNome(rsProcedimentoBanco.getString("banco_nome"));

				procedimentoBanco.setBanco(banco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoBanco, stmt, conn);
		return procedimentoBanco;
	}

	private void getProcedimentoBanco(Collection<ProcedimentoBanco> procedimentosBanco)	throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		ProcedimentoConferencia procedimentoConferencia = new ProcedimentoConferencia();		
		Banco banco = new Banco();
		ProcedimentoBanco procedimentoBanco = new ProcedimentoBanco();

		empresa.setEmpresa_id(rsProcedimentoBanco.getLong("empresa_id"));
		empresa.setNome(rsProcedimentoBanco.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsProcedimentoBanco.getLong("organizacao_id"));
		organizacao.setNome(rsProcedimentoBanco.getString("organizacao_nome"));

		procedimentoConferencia.setProcedimentoConferencia_id(rsProcedimentoBanco.getLong("procedimento_id"));
		procedimentoConferencia.setNome(rsProcedimentoBanco.getString("procedimentoconferencia_nome"));

		banco.setBanco_id(rsProcedimentoBanco.getLong("banco_id"));
		banco.setNome(rsProcedimentoBanco.getString("banco_nome"));

		procedimentoBanco.setEmpresa(empresa);
		procedimentoBanco.setOrganizacao(organizacao);
		procedimentoBanco.setProcedimento(procedimentoConferencia);
		procedimentoBanco.setBanco(banco);					

		procedimentoBanco.setIsActive(rsProcedimentoBanco.getBoolean("isactive"));
		
		procedimentosBanco.add(procedimentoBanco);

	}

}