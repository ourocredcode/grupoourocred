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
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.OrganizacaoInfo;

@Component
public class OrganizacaoInfoDao extends Dao<OrganizacaoInfo> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOrganizacaoInfo;

	private final String sqlOrganizacaoInfo = "SELECT ORGANIZACAOINFO.organizacao_id, ORGANIZACAOINFO.nome, ORGANIZACAOINFO.empresa_id FROM ORGANIZACAOINFO (NOLOCK)";

	public OrganizacaoInfoDao(Session session, ConnJDBC conexao) {

		super(session, OrganizacaoInfo.class);
		this.conexao = conexao;

	}

	public Collection<OrganizacaoInfo> buscaAllOrganizacaoInfo() {

		String sql = sqlOrganizacaoInfo;

		this.conn = this.conexao.getConexao();

		Collection<OrganizacaoInfo> infosOrganizacao = new ArrayList<OrganizacaoInfo>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsOrganizacaoInfo = this.stmt.executeQuery();

			while (rsOrganizacaoInfo.next()) {
			
				OrganizacaoInfo infoOrganizacao = new OrganizacaoInfo();

				infoOrganizacao.setOrganizacao_id(rsOrganizacaoInfo.getLong("organizacao_id"));
				infoOrganizacao.setNome(rsOrganizacaoInfo.getString("nome"));

				infosOrganizacao.add(infoOrganizacao);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoInfo, stmt, conn);
		return infosOrganizacao;
	}

	public OrganizacaoInfo buscaOrganizacaoById(Long organizacao_id) {

		String sql = sqlOrganizacaoInfo;

		if (organizacao_id != null)
			sql += " WHERE ORGANIZACAOINFO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		OrganizacaoInfo organizacaoInfo = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, organizacao_id);

			this.rsOrganizacaoInfo = this.stmt.executeQuery();			

			while (rsOrganizacaoInfo.next()) {

				organizacaoInfo = new OrganizacaoInfo();
				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsOrganizacaoInfo.getLong("organizacao_id"));
				organizacaoInfo.setOrganizacao_id(organizacao_id);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoInfo, stmt, conn);
		return organizacaoInfo;
	}

	public OrganizacaoInfo buscaOrganizacaoInfoByEmOrNo(Long empresa,Long organizacao, String nome) {

		String sql = sqlOrganizacaoInfo;

		if (empresa != null)
			sql += " AND ORGANIZACAOINFO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND ORGANIZACAOINFO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (ORGANIZACAOINFO.nome like ?)";

		this.conn = this.conexao.getConexao();

		OrganizacaoInfo infoOrganizacao = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsOrganizacaoInfo = this.stmt.executeQuery();

			while (rsOrganizacaoInfo.next()) {

				infoOrganizacao = new OrganizacaoInfo();

				infoOrganizacao.setOrganizacao_id(rsOrganizacaoInfo.getLong("organizacao_id"));
				infoOrganizacao.setNome(rsOrganizacaoInfo.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoInfo, stmt, conn);
		return infoOrganizacao;
	}

}