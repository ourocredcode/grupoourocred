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
import br.com.sgo.modelo.ClassificacaoParceiro;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class ClassificacaoParceiroDao extends Dao<ClassificacaoParceiro> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsClassificacaoParceiro;
	
	private final String sqlClassificacaoParceiro = "SELECT CLASSIFICACAOPARCEIRO.classificacaoparceiro_id, CLASSIFICACAOPARCEIRO.nome from CLASSIFICACAOPARCEIRO (NOLOCK) "; 

	private final String sqlClassificacoesParceiro = "SELECT EMPRESA.empresa_id, EMPRESA.nome AS empresa_nome, ORGANIZACAO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
								", CLASSIFICACAOPARCEIRO.classificacaoparceiro_id, CLASSIFICACAOPARCEIRO.nome AS classificacaoparceiro_nome, CLASSIFICACAOPARCEIRO.isactive "+
								" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN CLASSIFICACAOPARCEIRO (NOLOCK) "+ 
								" ON EMPRESA.empresa_id = CLASSIFICACAOPARCEIRO.empresa_id) ON ORGANIZACAO.organizacao_id = CLASSIFICACAOPARCEIRO.organizacao_id ";

	public ClassificacaoParceiroDao(Session session, ConnJDBC conexao) {

		super(session, ClassificacaoParceiro.class);
		this.conexao = conexao;

	}

	public Collection<ClassificacaoParceiro> buscaAllClassificacaoParceiroByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlClassificacoesParceiro;

		if (empresa_id != null)
			sql += " WHERE CLASSIFICACAOPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CLASSIFICACAOPARCEIRO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ClassificacaoParceiro> classificacoesParceiro = new ArrayList<ClassificacaoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsClassificacaoParceiro = this.stmt.executeQuery();

			while (rsClassificacaoParceiro.next()) {

				getClassificacaoParceiro(classificacoesParceiro);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsClassificacaoParceiro, stmt, conn);
		return classificacoesParceiro;
	}
	
	public Collection<ClassificacaoParceiro> buscaClassificacaoParceiro(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlClassificacoesParceiro;

		if (empresa_id != null)
			sql += " WHERE CLASSIFICACAOPARCEIRO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CLASSIFICACAOPARCEIRO.organizacao_id = ?";
		if (nome != null)
			sql += " AND CLASSIFICACAOPARCEIRO.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<ClassificacaoParceiro> classificacoesParceiro = new ArrayList<ClassificacaoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsClassificacaoParceiro = this.stmt.executeQuery();

			while (rsClassificacaoParceiro.next()) {

				getClassificacaoParceiro(classificacoesParceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsClassificacaoParceiro, stmt, conn);

		return classificacoesParceiro;

	}

	private void getClassificacaoParceiro(Collection<ClassificacaoParceiro> classificacoesParceiro)throws SQLException {

		ClassificacaoParceiro classificacaoParceiro = new ClassificacaoParceiro();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao(); 
		
		empresa.setEmpresa_id(rsClassificacaoParceiro.getLong("empresa_id"));
		empresa.setNome(rsClassificacaoParceiro.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsClassificacaoParceiro.getLong("organizacao_id"));
		organizacao.setNome(rsClassificacaoParceiro.getString("organizacao_nome"));				
		
		classificacaoParceiro.setEmpresa(empresa);
		classificacaoParceiro.setOrganizacao(organizacao);
		
		classificacaoParceiro.setClassificacaoParceiro_id(rsClassificacaoParceiro.getLong("classificacaoparceiro_id"));
		classificacaoParceiro.setNome(rsClassificacaoParceiro.getString("classificacaoparceiro_nome"));

		classificacoesParceiro.add(classificacaoParceiro);

	}

}