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

@Component
public class OrganizacaoDao extends Dao<Organizacao> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOrganizacoes;
	
	public OrganizacaoDao(Session session,ConnJDBC conexao) {
		super(session, Organizacao.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<Organizacao> buscaOrganizacoes(Long empresa_id, String org_nome){

		String sql = "select ORGANIZACAO.organizacao_id, ORGANIZACAO.nome from ORGANIZACAO (NOLOCK) " +
				"	WHERE ORGANIZACAO.empresa_id = ? AND ORGANIZACAO.nome like ? ";
		this.conn = this.conexao.getConexao();
		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setString(2,"%"+  org_nome + "%");
			this.rsOrganizacoes = this.stmt.executeQuery();
			
			while (rsOrganizacoes.next()) {
				Organizacao organizacao = new Organizacao();
				
				organizacao.setOrganizacao_id(rsOrganizacoes.getLong("organizacao_id"));
				organizacao.setNome(rsOrganizacoes.getString("nome"));
				
				organizacoes.add(organizacao);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOrganizacoes, stmt, conn);

		return organizacoes;

	}

}