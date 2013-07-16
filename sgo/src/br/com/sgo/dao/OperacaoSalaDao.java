package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Operacao;
import br.com.sgo.modelo.Sala;
import br.com.sgo.modelo.OperacaoSala;

@Component
public class OperacaoSalaDao extends Dao<OperacaoSala> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOperacaoSala;
	
	private final String sqlOperacaoSala = " SELECT OPERACAOSALA.empresa_id, OPERACAOSALA.organizacao_id, OPERACAOSALA.sala_id, OPERACAOSALA.operacao_id FROM OPERACAOSALA (NOLOCK) ";
	
	private final String sqlOperacaoSalas = "SELECT OPERACAOSALA.empresa_id, EMPRESA.nome, OPERACAOSALA.organizacao_id " +
			", ORGANIZACAO.nome, OPERACAOSALA.operacao_id, OPERACAO.nome, OPERACAOSALA.sala_id " +
			", SALA.nome, OPERACAOSALA.isactive, OPERACAOSALA.nome "+
			" FROM (((EMPRESA INNER JOIN OPERACAOSALA ON EMPRESA.empresa_id = OPERACAOSALA.empresa_id) " +
			" INNER JOIN ORGANIZACAO ON OPERACAOSALA.organizacao_id = ORGANIZACAO.organizacao_id) " +
			" INNER JOIN OPERACAO ON OPERACAOSALA.operacao_id = OPERACAO.operacao_id) " +
			" INNER JOIN SALA ON OPERACAOSALA.sala_id = SALA.sala_id ";

	public OperacaoSalaDao(Session session, ConnJDBC conexao) {

		super(session, OperacaoSala.class);
		this.conexao = conexao;

	}

	public OperacaoSala buscaOperacaoSalaByEmpresaOrganizacaoOperacaoSala(Long empresa_id, Long organizacao_id, Long sala_id, Long operacao_id) {

		String sql = sqlOperacaoSala; 

		this.conn = this.conexao.getConexao();
		
		if (empresa_id != null)
			sql += " WHERE OPERACAOSALA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND OPERACAOSALA.organizacao_id = ?";
		if (sala_id != null)
			sql += " AND OPERACAOSALA.sala_id = ?";
		if (operacao_id != null)
			sql += " AND OPERACAOSALA.operacao_id = ?";

		OperacaoSala operacaoSala = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, sala_id);
			this.stmt.setLong(4, operacao_id);

			this.rsOperacaoSala = this.stmt.executeQuery();

			while (rsOperacaoSala.next()) {

				operacaoSala = new OperacaoSala();				
				Operacao operacao = new Operacao();
				operacao.setOperacao_id(rsOperacaoSala.getLong("operacao_id"));				
				operacaoSala.setOperacao(operacao);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOperacaoSala, stmt, conn);
		return operacaoSala;
	}

	public Collection<OperacaoSala> buscaAllOperacaoSalaByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlOperacaoSalas;

		if (empresa_id != null)
			sql += " WHERE OPERACAOSALA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND OPERACAOSALA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<OperacaoSala> operacaoSalas = new ArrayList<OperacaoSala>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsOperacaoSala = this.stmt.executeQuery();

			while (rsOperacaoSala.next()) {

				getOperacaoSala(operacaoSalas);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOperacaoSala, stmt, conn);

		return operacaoSalas;

	}
	
	public void insert(OperacaoSala operacaoSala) throws SQLException {

		String sql = "INSERT INTO OPERACAOSALA (empresa_id, organizacao_id, sala_id, operacao_id, created, updated, createdby, updatedby, isactive) "
				+ "    VALUES (?,?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, operacaoSala.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, operacaoSala.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, operacaoSala.getSala().getSala_id());
			this.stmt.setLong(4, operacaoSala.getOperacao().getOperacao_id());
			this.stmt.setTimestamp(5, new Timestamp(operacaoSala.getCreated().getTimeInMillis()));
			this.stmt.setTimestamp(6, new Timestamp(operacaoSala.getUpdated().getTimeInMillis()));
			this.stmt.setLong(7, operacaoSala.getCreatedBy().getUsuario_id());
			this.stmt.setLong(8, operacaoSala.getUpdatedBy().getUsuario_id());
			this.stmt.setBoolean(9, operacaoSala.getIsActive());

			this.stmt.executeUpdate();

			this.conn.commit();

		} catch (SQLException e) {

			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}
		this.conexao.closeConnection(stmt, conn);
	}
	
	private void getOperacaoSala(Collection<OperacaoSala> operacaoSalas) throws SQLException {

		OperacaoSala operacaoSala = new OperacaoSala();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Operacao operacao = new Operacao();
		Sala sala = new Sala();
		
		empresa.setEmpresa_id(rsOperacaoSala.getLong("empresa_id"));
		empresa.setNome(rsOperacaoSala.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsOperacaoSala.getLong("organizacao_id"));
		organizacao.setNome(rsOperacaoSala.getString("organizacao_nome"));				
		
		operacao.setOperacao_id(rsOperacaoSala.getLong("operacao_id"));
		operacao.setNome(rsOperacaoSala.getString("operacao_nome"));				

		sala.setSala_id(rsOperacaoSala.getLong("sala_id"));
		sala.setNome(rsOperacaoSala.getString("sala_nome"));	
		
		operacaoSala.setEmpresa(empresa);
		operacaoSala.setOrganizacao(organizacao);
		operacaoSala.setOperacao(operacao);
		operacaoSala.setSala(sala);
		operacaoSala.setIsActive(rsOperacaoSala.getBoolean("isactive"));

		operacaoSalas.add(operacaoSala);
	}

}