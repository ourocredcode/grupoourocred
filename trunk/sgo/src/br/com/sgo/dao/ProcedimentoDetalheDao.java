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
import br.com.sgo.modelo.Agente;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.ModeloProcedimento;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoConferencia;
import br.com.sgo.modelo.ProcedimentoDetalhe;

@Component
public class ProcedimentoDetalheDao extends Dao<ProcedimentoDetalhe> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProcedimentoDetalhe;

	private final String sqlProcedimentoDetalhe = "SELECT PROCEDIMENTODETALHE.procedimentobancodetalhe_id, PROCEDIMENTODETALHE.empresa_id, EMPRESA.nome as empresa_nome "+
							", PROCEDIMENTODETALHE.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
							", PROCEDIMENTODETALHE.procedimentobanco_id, PROCEDIMENTOBANCO.nome as procedimentobanco_nome "+
							", PROCEDIMENTODETALHE.banco_id, BANCO.nome as banco_nome, PROCEDIMENTODETALHE.acao, PROCEDIMENTODETALHE.procedimento, PROCEDIMENTODETALHE.isactive "+
							" FROM (((PROCEDIMENTOBANCO (NOLOCK) INNER JOIN (ORGANIZACAO (NOLOCK) INNER JOIN (MODELOPROCEDIMENTO (NOLOCK) "+
							" INNER JOIN PROCEDIMENTODETALHE (NOLOCK) ON MODELOPROCEDIMENTO.modeloprocedimento_id = PROCEDIMENTODETALHE.modeloprocedimento_id) ON ORGANIZACAO.organizacao_id = PROCEDIMENTODETALHE.organizacao_id) ON PROCEDIMENTOBANCO.procedimentobanco_id = PROCEDIMENTODETALHE.procedimentobanco_id) "+ 
							" INNER JOIN TIPOPROCEDIMENTO (NOLOCK) ON PROCEDIMENTOBANCO.tipoprocedimento_id = TIPOPROCEDIMENTO.tipoprocedimento_id) "+
							" INNER JOIN EMPRESA (NOLOCK) ON PROCEDIMENTODETALHE.empresa_id = EMPRESA.empresa_id) "+
							" INNER JOIN BANCO (NOLOCK) ON PROCEDIMENTODETALHE.banco_id = BANCO.banco_id ";

	public ProcedimentoDetalheDao(Session session, ConnJDBC conexao) {
		super(session, ProcedimentoDetalhe.class);
		this.conexao = conexao;
	}

	public Collection<ProcedimentoDetalhe> buscaAllProcedimentoDetalhe(Long empresa_id, Long organizacao_id) {

		String sql = sqlProcedimentoDetalhe;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTODETALHE.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoDetalhe> ProcedimentosDetalhe = new ArrayList<ProcedimentoDetalhe>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsProcedimentoDetalhe = this.stmt.executeQuery();

			while (rsProcedimentoDetalhe.next()) {

				getProcedimentoDetalhe(ProcedimentosDetalhe);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoDetalhe, stmt, conn);
		return ProcedimentosDetalhe;

	}
	
	public Collection<ProcedimentoDetalhe> buscaProcedimentoDetalheByProcedimentoBanco(Long procedimentoBanco_id) {

		String sql = sqlProcedimentoDetalhe;

		if (procedimentoBanco_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.procedimentoBanco_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoDetalhe> procedimentosDetalhe = new ArrayList<ProcedimentoDetalhe>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, procedimentoBanco_id);

			this.rsProcedimentoDetalhe = this.stmt.executeQuery();

			while (rsProcedimentoDetalhe.next()) {

				Banco banco = new Banco();
				banco.setBanco_id(rsProcedimentoDetalhe.getLong("banco_id"));
				banco.setNome(rsProcedimentoDetalhe.getString("banco_nome"));

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoDetalhe, stmt, conn);
		return procedimentosDetalhe;

	}
	
	public Collection<ProcedimentoDetalhe> buscaProcedimentoDetalheByNome(Long empresa_id, Long organizacao_id, Integer acao) {

		String sql = sqlProcedimentoDetalhe;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTODETALHE.organizacao_id = ?";	
		if (acao != null)
			sql += " AND (PROCEDIMENTODETALHE.acao = ?)";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoDetalhe> procedimentosBanco = new ArrayList<ProcedimentoDetalhe>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);			
			this.stmt.setInt(3, acao );

			this.rsProcedimentoDetalhe = this.stmt.executeQuery();

			while (rsProcedimentoDetalhe.next()) {

				getProcedimentoDetalhe(procedimentosBanco);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoDetalhe, stmt, conn);
		return procedimentosBanco;

	}

	public ProcedimentoDetalhe buscaProcedimentoDetalheByEmpresaOrganizacaoProcedimentoAcao(Long empresa_id, Long organizacao_id, Long procedimento_id, Long banco_id, Long modeloProcedimento_id, Long agente_id, Integer acao) {

		String sql = sqlProcedimentoDetalhe;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTODETALHE.organizacao_id = ?";		
		if (procedimento_id != null)
			sql += " AND PROCEDIMENTODETALHE.procedimentobanco_id = ?";
		if (banco_id != null)
			sql += " AND PROCEDIMENTODETALHE.banco_id = ?";
		if (modeloProcedimento_id != null)
			sql += " AND PROCEDIMENTODETALHE.modeloprocedimento_id = ?";
		if (agente_id != null)
			sql += " AND PROCEDIMENTODETALHE.agente_id = ?";
		if (acao != null)
			sql += " AND (PROCEDIMENTODETALHE.acao = ?)";

		this.conn = this.conexao.getConexao();

		ProcedimentoDetalhe procedimentoDetalhe = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, procedimento_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, modeloProcedimento_id);
			this.stmt.setLong(6, agente_id);
			this.stmt.setInt(7,  acao );

			this.rsProcedimentoDetalhe = this.stmt.executeQuery();

			while (rsProcedimentoDetalhe.next()) {

				procedimentoDetalhe = new ProcedimentoDetalhe();
				procedimentoDetalhe.setProcedimentoDetalhe_id(rsProcedimentoDetalhe.getLong("procedimentobanco_id"));
				procedimentoDetalhe.setAcao(rsProcedimentoDetalhe.getInt("acao"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoDetalhe, stmt, conn);
		return procedimentoDetalhe;
	}
	
	public Collection<ProcedimentoDetalhe> buscaProcedimentoDetalheByBancoModeloProcedimento(Long modeloprocedimento_id, Long banco_id){
		
		String sql = "SELECT PROCEDIMENTODETALHE.procedimentodetalhe_id, PROCEDIMENTODETALHE.isactive, PROCEDIMENTODETALHE.acao, PROCEDIMENTODETALHE.procedimento_id, PROCEDIMENTODETALHE.banco_id "+
						", PROCEDIMENTODETALHE.modeloprocedimento_id, PROCEDIMENTODETALHE.detalheprocedimento "+
						", PROCEDIMENTODETALHE.empresa_id, EMPRESA.nome AS empresa_nome, PROCEDIMENTODETALHE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						", PROCEDIMENTODETALHE.agente_id, AGENTE.nome AS agente_nome, BANCO.nome as banco_nome , BANCO.banco_id "+
						" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
						" INNER JOIN PROCEDIMENTODETALHE (NOLOCK) ON EMPRESA.empresa_id = PROCEDIMENTODETALHE.empresa_id) ON ORGANIZACAO.organizacao_id = PROCEDIMENTODETALHE.organizacao_id) "+ 
						" INNER JOIN AGENTE (NOLOCK) ON PROCEDIMENTODETALHE.agente_id = AGENTE.agente_id) " +
						" INNER JOIN BANCO (NOLOCK) ON PROCEDIMENTODETALHE.banco_id = BANCO.banco_id ";

		if (modeloprocedimento_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.modeloprocedimento_id = ?";
		if (banco_id != null)
			sql += " AND PROCEDIMENTODETALHE.banco_id = ?  ";
		
		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoDetalhe> procedimentosDetalhe = new ArrayList<ProcedimentoDetalhe>();

		try {

			this.stmt = conn.prepareStatement(sql);
	
			this.stmt.setLong(1, modeloprocedimento_id);
			this.stmt.setLong(2, banco_id);

			this.rsProcedimentoDetalhe = this.stmt.executeQuery();

			while (rsProcedimentoDetalhe.next()) {

				ProcedimentoDetalhe procedimentoDetalhe = new ProcedimentoDetalhe();
				
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();		
				ProcedimentoConferencia procedimento = new ProcedimentoConferencia();
				ModeloProcedimento modeloProcedimento = new ModeloProcedimento();
				Agente agente = new Agente();
				Banco banco = new Banco();

				empresa.setEmpresa_id(rsProcedimentoDetalhe.getLong("empresa_id"));
				empresa.setNome(rsProcedimentoDetalhe.getString("empresa_nome"));

				organizacao.setOrganizacao_id(rsProcedimentoDetalhe.getLong("organizacao_id"));
				organizacao.setNome(rsProcedimentoDetalhe.getString("organizacao_nome"));

				banco.setBanco_id(rsProcedimentoDetalhe.getLong("banco_id"));
				banco.setNome(rsProcedimentoDetalhe.getString("banco_nome"));

				modeloProcedimento.setModeloProcedimento_id(rsProcedimentoDetalhe.getLong("modeloprocedimento_id"));				

				agente.setAgente_id(rsProcedimentoDetalhe.getLong("agente_id"));
				agente.setNome(rsProcedimentoDetalhe.getString("agente_nome"));

				procedimentoDetalhe.setEmpresa(empresa);
				procedimentoDetalhe.setOrganizacao(organizacao);		
				procedimentoDetalhe.setProcedimento(procedimento);
				procedimentoDetalhe.setModeloProcedimento(modeloProcedimento);
				procedimentoDetalhe.setAgente(agente);
				procedimentoDetalhe.setBanco(banco);

				procedimentoDetalhe.setAcao(rsProcedimentoDetalhe.getInt("acao"));		
				procedimentoDetalhe.setProcedimentoDetalhe_id(rsProcedimentoDetalhe.getLong("procedimentodetalhe_id"));
				procedimentoDetalhe.setDetalheProcedimento(rsProcedimentoDetalhe.getString("detalheprocedimento"));
				procedimentoDetalhe.setAcao(rsProcedimentoDetalhe.getInt("acao"));		
				procedimentoDetalhe.setIsActive(rsProcedimentoDetalhe.getBoolean("isactive"));

				procedimentosDetalhe.add(procedimentoDetalhe);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsProcedimentoDetalhe, stmt, conn);

		return procedimentosDetalhe;

	}


	private void getProcedimentoDetalhe(Collection<ProcedimentoDetalhe> procedimentosDetalhe)	throws SQLException {

		ProcedimentoDetalhe procedimentoDetalhe = new ProcedimentoDetalhe();
		
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();		
		ProcedimentoConferencia procedimento = new ProcedimentoConferencia();
		Banco banco = new Banco();
		ModeloProcedimento modeloProcedimento = new ModeloProcedimento();
		Agente agente = new Agente();

		empresa.setEmpresa_id(rsProcedimentoDetalhe.getLong("empresa_id"));
		empresa.setNome(rsProcedimentoDetalhe.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsProcedimentoDetalhe.getLong("organizacao_id"));
		organizacao.setNome(rsProcedimentoDetalhe.getString("organizacao_nome"));

		procedimento.setProcedimentoConferencia_id(rsProcedimentoDetalhe.getLong("procedimentoconferencia_id"));
		procedimento.setNome(rsProcedimentoDetalhe.getString("procedimentoconferencia_nome"));

		banco.setBanco_id(rsProcedimentoDetalhe.getLong("banco_id"));
		banco.setNome(rsProcedimentoDetalhe.getString("banco_nome"));

		modeloProcedimento.setModeloProcedimento_id(rsProcedimentoDetalhe.getLong("modeloprocedimento_id"));
		modeloProcedimento.setNome(rsProcedimentoDetalhe.getString("modeloprocedimento_nome"));
		
		agente.setAgente_id(rsProcedimentoDetalhe.getLong("agente_id"));
		agente.setNome(rsProcedimentoDetalhe.getString("agente_nome"));

		procedimentoDetalhe.setEmpresa(empresa);
		procedimentoDetalhe.setOrganizacao(organizacao);		
		procedimentoDetalhe.setProcedimento(procedimento);
		procedimentoDetalhe.setBanco(banco);
		procedimentoDetalhe.setModeloProcedimento(modeloProcedimento);
		procedimentoDetalhe.setAgente(agente);
		
		procedimentoDetalhe.setAcao(rsProcedimentoDetalhe.getInt("acao"));		
		procedimentoDetalhe.setProcedimentoDetalhe_id(rsProcedimentoDetalhe.getLong("procedimentodetalhe_id"));
		procedimentoDetalhe.setDetalheProcedimento(rsProcedimentoDetalhe.getString("detalheprocedimento"));
		procedimentoDetalhe.setAcao(rsProcedimentoDetalhe.getInt("acao"));		
		procedimentoDetalhe.setIsActive(rsProcedimentoDetalhe.getBoolean("isactive"));

		procedimentosDetalhe.add(procedimentoDetalhe);

	}

}