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
import br.com.sgo.modelo.Agente;

@Component
public class AgenteDao extends Dao<Agente> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsAgentes;

	private final String sqlAgente = "SELECT AGENTE.agente_id, AGENTE.empresa_id, AGENTE.organizacao_id, AGENTE.nome, AGENTE.isactive, AGENTE.iscontrole FROM AGENTE (NOLOCK) ";
	
	private final String sqlAgentes = " SELECT AGENTE.empresa_id, EMPRESA.nome AS empresa_nome, AGENTE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome " +
				", AGENTE.nome, AGENTE.agente_id, AGENTE.nome AS agente_nome, AGENTE.isactive, AGENTE.iscontrole "+
				" FROM (AGENTE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON AGENTE.empresa_id = EMPRESA.empresa_id) INNER JOIN ORGANIZACAO (NOLOCK) ON AGENTE.organizacao_id = ORGANIZACAO.organizacao_id ";

	public AgenteDao(Session session, ConnJDBC conexao) {

		super(session, Agente.class);		
		this.conexao = conexao;		

	}
	
	public Collection<Agente> buscaAllAgenteByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlAgentes;
		
		if (empresa_id != null)
			sql += " WHERE AGENTE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND AGENTE.organizacao_id = ? ORDER BY AGENTE.nome";

		this.conn = this.conexao.getConexao();

		Collection<Agente> agentes = new ArrayList<Agente>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsAgentes = this.stmt.executeQuery();

			while (rsAgentes.next()) {

				getAgente(agentes);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsAgentes, stmt, conn);

		return agentes;

	}

	public Collection<Agente> buscaAgenteToControleBancoByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlAgentes;
		
		if (empresa_id != null)
			sql += " WHERE AGENTE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND AGENTE.organizacao_id = ? AND AGENTE.iscontrole = 1 ORDER BY AGENTE.nome";

		this.conn = this.conexao.getConexao();

		Collection<Agente> agentes = new ArrayList<Agente>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsAgentes = this.stmt.executeQuery();

			while (rsAgentes.next()) {

				getAgente(agentes);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsAgentes, stmt, conn);

		return agentes;

	}

	public Collection<Agente> buscaAgentesByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlAgentes;
		
		if (empresa_id != null)
			sql += " WHERE AGENTE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND AGENTE.organizacao_id = ?";
		if (nome != null)
			sql += " AND AGENTE.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Agente> agentes = new ArrayList<Agente>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsAgentes = this.stmt.executeQuery();

			while (rsAgentes.next()) {

				getAgente(agentes);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsAgentes, stmt, conn);

		return agentes;

	}

	public Agente buscaAgenteByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlAgente;

		if (empresa_id != null)
			sql += " WHERE AGENTE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND AGENTE.organizacao_id = ? ";
		if (nome != null)
			sql += " AND AGENTE.nome = ? ";

		this.conn = this.conexao.getConexao();

		Agente agente = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsAgentes = this.stmt.executeQuery();

			while (rsAgentes.next()) {

				agente = new Agente();

				agente.setAgente_id(rsAgentes.getLong("agente_id"));
				agente.setNome(rsAgentes.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsAgentes, stmt, conn);
		return agente;
	}

	private void getAgente(Collection<Agente> agentes)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Agente agente = new Agente();

		empresa.setEmpresa_id(rsAgentes.getLong("empresa_id"));
		empresa.setNome(rsAgentes.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsAgentes.getLong("organizacao_id"));		
		organizacao.setNome(rsAgentes.getString("organizacao_nome"));
		
		agente.setEmpresa(empresa);
		agente.setOrganizacao(organizacao);
		agente.setAgente_id(rsAgentes.getLong("agente_id"));
		agente.setNome(rsAgentes.getString("agente_nome"));
		agente.setIsActive(rsAgentes.getBoolean("isactive"));
		agente.setIsControle(rsAgentes.getBoolean("iscontrole"));

		agentes.add(agente);

	}

}