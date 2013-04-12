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
import br.com.sgo.modelo.Cor;

@Component
public class CorDao extends Dao<Cor> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCor;

	private final String sqlCor = "SELECT COR.cor_id, COR.nome, COR.empresa_id"
			+ ", COR.organizacao_id FROM COR (NOLOCK)";

	public CorDao(Session session, ConnJDBC conexao) {
		super(session, Cor.class);
		this.conexao = conexao;
	}

	public Collection<Cor> buscaAllCor() {
		String sql = sqlCor;
		this.conn = this.conexao.getConexao();
		Collection<Cor> cores = new ArrayList<Cor>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsCor = this.stmt.executeQuery();
			while (rsCor.next()) {
				Cor cor = new Cor();
				cor.setCor_id(rsCor.getLong("cor_id"));
				cor.setNome(rsCor.getString("nome"));
				cores.add(cor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsCor, stmt, conn);
		return cores;
	}

	public Collection<Cor> buscaCorByNome(String nome) {
		String sql = sqlCor;
		if (nome != null)
			sql += " WHERE COR.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Cor> cores = new ArrayList<Cor>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsCor = this.stmt.executeQuery();
			Cor cor = new Cor();
			while (rsCor.next()) {
				cor.setCor_id(rsCor.getLong("cor_id"));
				cor.setNome(rsCor.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsCor, stmt, conn);
		return cores;
	}

	public Cor buscaCorByEmOrNo(Long empresa, Long organizacao, String nome) {
		String sql = sqlCor;
		if (empresa != null)
			sql += " AND COR.empresa_id = ?";
		if (organizacao != null)
			sql += " AND COR.organizacao_id = ?";
		if (nome != null)
			sql += " AND (COR.nome like ?)";
		this.conn = this.conexao.getConexao();
		Cor cor = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsCor = this.stmt.executeQuery();
			while (rsCor.next()) {
				cor = new Cor();
				cor.setCor_id(rsCor.getLong("cor_id"));
				cor.setNome(rsCor.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsCor, stmt, conn);
		return cor;
	}

}