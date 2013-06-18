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
import br.com.sgo.modelo.GrupoParceiro;
import br.com.sgo.modelo.Organizacao;

@Component
public class GrupoParceiroDao extends Dao<GrupoParceiro> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsGrupoParceiro;

	private final String sqlGruposParceiro = "SELECT EMPRESA.empresa_id, EMPRESA.nome AS empresa_nome, ORGANIZACAO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
								", GRUPOPARCEIRO.grupoparceiro_id, GRUPOPARCEIRO.nome AS grupoparceiro_nome, GRUPOPARCEIRO.isactive "+
								" FROM ORGANIZACAO INNER JOIN (EMPRESA INNER JOIN GRUPOPARCEIRO ON EMPRESA.empresa_id = GRUPOPARCEIRO.empresa_id) ON ORGANIZACAO.organizacao_id = GRUPOPARCEIRO.organizacao_id ";

	public GrupoParceiroDao(Session session, ConnJDBC conexao) {

		super(session, GrupoParceiro.class);
		this.conexao = conexao;

	}

	public Collection<GrupoParceiro> buscaAllGrupoParceiroByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlGruposParceiro;

		if (empresa_id != null)
			sql += " WHERE GRUPOPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND GRUPOPARCEIRO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<GrupoParceiro> gruposParceiro = new ArrayList<GrupoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsGrupoParceiro = this.stmt.executeQuery();

			while (rsGrupoParceiro.next()) {

				getGrupoParceiro(gruposParceiro);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsGrupoParceiro, stmt, conn);
		return gruposParceiro;
	}

	public Collection<GrupoParceiro> buscaGrupoParceiroByEmpOrgNome(Long empresa_id,Long organizacao_id, String nome) {

		String sql = sqlGruposParceiro;

		this.conn = this.conexao.getConexao();

		Collection<GrupoParceiro> gruposParceiro = new ArrayList<GrupoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsGrupoParceiro = this.stmt.executeQuery();

			while (rsGrupoParceiro.next()) {

				getGrupoParceiro(gruposParceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoParceiro, stmt, conn);

		return gruposParceiro;

	}

	private void getGrupoParceiro(Collection<GrupoParceiro> gruposParceiro)throws SQLException {

		GrupoParceiro grupoParceiro = new GrupoParceiro();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao(); 
		
		empresa.setEmpresa_id(rsGrupoParceiro.getLong("empresa_id"));
		empresa.setNome(rsGrupoParceiro.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsGrupoParceiro.getLong("organizacao_id"));
		organizacao.setNome(rsGrupoParceiro.getString("organizacao_nome"));				
		
		grupoParceiro.setEmpresa(empresa);
		grupoParceiro.setOrganizacao(organizacao);
		
		grupoParceiro.setGrupoParceiro_id(rsGrupoParceiro.getLong("grupoparceiro_id"));
		grupoParceiro.setNome(rsGrupoParceiro.getString("grupoparceiro_nome"));

		gruposParceiro.add(grupoParceiro);

	}
	
}