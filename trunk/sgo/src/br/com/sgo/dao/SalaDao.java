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
import br.com.sgo.modelo.Sala;

@Component
public class SalaDao extends Dao<Sala> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsSalas;

	private final String sqlSala = "SELECT SALA.sala_id, SALA.empresa_id, SALA.organizacao_id" +
			", SALA.isactive, SALA.nome FROM SALA (NOLOCK) ";
	
	private final String sqlSalas = " SELECT SALA.empresa_id, EMPRESA.nome AS empresa_nome, SALA.organizacao_id "+
				", ORGANIZACAO.nome AS organizacao_nome, SALA.sala_id, SALA.nome AS sala_nome, SALA.isactive "+
				" FROM (SALA (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON SALA.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON SALA.organizacao_id = ORGANIZACAO.organizacao_id ";


	public SalaDao(Session session, ConnJDBC conexao) {

		super(session, Sala.class);		
		this.conexao = conexao;		

	}
	
	public Collection<Sala> buscaAllSala(Long empresa_id, Long organizacao_id) {

		String sql = sqlSalas;
		
		if (empresa_id != null)
			sql += " WHERE SALA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND SALA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Sala> salas = new ArrayList<Sala>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsSalas = this.stmt.executeQuery();

			while (rsSalas.next()) {
				
				getSala(salas);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsSalas, stmt, conn);

		return salas;

	}

	public Sala buscaSalaByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlSala;

		if (empresa_id != null)
			sql += " WHERE SALA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND SALA.organizacao_id = ? ";
		if (nome != null)
			sql += " AND SALA.nome = ? ";

		this.conn = this.conexao.getConexao();

		Sala sala = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsSalas = this.stmt.executeQuery();

			while (rsSalas.next()) {

				sala = new Sala();

				sala.setSala_id(rsSalas.getLong("sala_id"));
				sala.setNome(rsSalas.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsSalas, stmt, conn);
		return sala;
	}

	public Collection<Sala> buscaSalas(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlSalas;

		if (empresa_id != null)
			sql += " WHERE SALA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND SALA.organizacao_id = ?";
		if (nome != null)
			sql += " AND SALA.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<Sala> salas = new ArrayList<Sala>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsSalas = this.stmt.executeQuery();

			while (rsSalas.next()) {

				Sala sala = new Sala();

				sala.setSala_id(rsSalas.getLong("sala_id"));
				sala.setNome(rsSalas.getString("sala_nome"));

				salas.add(sala);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsSalas, stmt, conn);

		return salas;

	}

	public Collection<Sala> buscasalas(Long empresa_id, Long organizacao_id) {

		String sql = sqlSalas;

		if (empresa_id != null)
			sql += " WHERE SALA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND SALA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Sala> salas = new ArrayList<Sala>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsSalas = this.stmt.executeQuery();

			while (rsSalas.next()) {

				Sala sala = new Sala();

				sala.setSala_id(rsSalas.getLong("sala_id"));
				sala.setNome(rsSalas.getString("sala_nome"));

				salas.add(sala);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsSalas, stmt, conn);

		return salas;

	}

	private void getSala(Collection<Sala> salas)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Sala sala = new Sala();

		empresa.setEmpresa_id(rsSalas.getLong("empresa_id"));
		empresa.setNome(rsSalas.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsSalas.getLong("organizacao_id"));		
		organizacao.setNome(rsSalas.getString("organizacao_nome"));
		
		sala.setEmpresa(empresa);
		sala.setOrganizacao(organizacao);
		sala.setSala_id(rsSalas.getLong("sala_id"));
		sala.setNome(rsSalas.getString("sala_nome"));
		sala.setIsActive(rsSalas.getBoolean("isactive"));

		salas.add(sala);

	}

}
