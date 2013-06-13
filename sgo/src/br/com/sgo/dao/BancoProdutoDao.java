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
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.BancoProduto;

@Component
public class BancoProdutoDao extends Dao<BancoProduto> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsBancoProduto;

	private final String sqlBancoProduto = "SELECT BANCOPRODUTO.bancoproduto_id, BANCOPRODUTO.empresa_id, BANCOPRODUTO.organizacao_id, BANCOPRODUTO.produto_id, BANCOPRODUTO.banco_id, BANCOPRODUTO.workflow_id, BANCOPRODUTO.isworkflow, BANCOPRODUTO.nome, BANCOPRODUTO.isactive FROM BANCOPRODUTO ";

	private final String sqlBancoProdutos = "SELECT BANCOPRODUTO.bancoproduto_id, BANCOPRODUTO.isactive, BANCOPRODUTO.isworkflow "+
							", BANCOPRODUTO.empresa_id, EMPRESA.nome AS empresa_nome "+
							", BANCOPRODUTO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
							", BANCOPRODUTO.workflow_id, WORKFLOW.nome AS workflow_nome "+
							", BANCOPRODUTO.produto_id, PRODUTO.nome AS produto_nome, BANCOPRODUTO.banco_id, BANCO.nome AS banco_nome "+
							" FROM ((((BANCOPRODUTO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON BANCOPRODUTO.empresa_id = EMPRESA.empresa_id) "+
							" INNER JOIN ORGANIZACAO (NOLOCK) ON BANCOPRODUTO.organizacao_id = ORGANIZACAO.organizacao_id) "+
							" INNER JOIN BANCO (NOLOCK) ON BANCOPRODUTO.banco_id = BANCO.banco_id) "+
							" INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTO.produto_id = PRODUTO.produto_id) "+
							" INNER JOIN WORKFLOW (NOLOCK) ON BANCOPRODUTO.workflow_id = WORKFLOW.workflow_id ";

	public BancoProdutoDao(Session session, ConnJDBC conexao) {

		super(session, BancoProduto.class);
		this.conexao = conexao;

	}

	public Collection<BancoProduto> buscaAllBancoProduto() {

		String sql = sqlBancoProdutos;

		this.conn = this.conexao.getConexao();

		Collection<BancoProduto> bancoProdutos = new ArrayList<BancoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsBancoProduto = this.stmt.executeQuery();

			while (rsBancoProduto.next()) {

				getBancoProduto(bancoProdutos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsBancoProduto, stmt, conn);

		return bancoProdutos;
	}
	
	public Collection<BancoProduto> buscaAllBancoProdutoByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlBancoProdutos;

		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<BancoProduto> bancoProdutos = new ArrayList<BancoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsBancoProduto = this.stmt.executeQuery();

			while (rsBancoProduto.next()) {

				getBancoProduto(bancoProdutos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsBancoProduto, stmt, conn);

		return bancoProdutos;
	}

	public BancoProduto buscaBancoProdutoByEmpresaOrganizacaoProdutoBancoWorkflow(Long empresa_id, Long organizacao_id, Long produto_id, Long banco_id, Long workflow_id ) {

		String sql = sqlBancoProduto;

		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTO.organizacao_id = ?";
		if (produto_id != null)
			sql += " AND BANCOPRODUTO.produto_id = ?";
		if (banco_id != null)
			sql += " AND BANCOPRODUTO.banco_id = ?";
		if (workflow_id != null)
			sql += " AND BANCOPRODUTO.workflow_id = ?";

		this.conn = this.conexao.getConexao();

		BancoProduto bancoProduto = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, produto_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, workflow_id);
			
			this.rsBancoProduto = this.stmt.executeQuery();

			while (rsBancoProduto.next()) {

				bancoProduto = new BancoProduto();

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsBancoProduto.getLong("workflow_id"));

				bancoProduto.setWorkflow(workflow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsBancoProduto, stmt, conn);

		return bancoProduto;
	}
	
	public BancoProduto buscaBancoProdutoByEmpresaOrganizacaoProdutoBanco(Long empresa_id, Long organizacao_id, Long produto_id, Long banco_id) {

		String sql = sqlBancoProduto;

		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTO.organizacao_id = ?";
		if (produto_id != null)
			sql += " AND BANCOPRODUTO.produto_id = ?";
		if (banco_id != null)
			sql += " AND BANCOPRODUTO.banco_id = ?";

		this.conn = this.conexao.getConexao();

		BancoProduto bancoProduto = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, produto_id);
			this.stmt.setLong(4, banco_id);
			
			this.rsBancoProduto = this.stmt.executeQuery();

			while (rsBancoProduto.next()) {

				bancoProduto = new BancoProduto();

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsBancoProduto.getLong("workflow_id"));

				bancoProduto.setWorkflow(workflow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsBancoProduto, stmt, conn);

		return bancoProduto;
	}

	private void getBancoProduto(Collection<BancoProduto> bancoProdutos)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Workflow workflow = new Workflow();
		BancoProduto bancoProduto = new BancoProduto();
		Banco banco = new Banco();
		Produto produto = new Produto();
		
		empresa.setEmpresa_id(rsBancoProduto.getLong("empresa_id"));
		empresa.setNome(rsBancoProduto.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsBancoProduto.getLong("organizacao_id"));
		organizacao.setNome(rsBancoProduto.getString("organizacao_nome"));

		workflow.setWorkflow_id(rsBancoProduto.getLong("workflow_id"));
		workflow.setNome(rsBancoProduto.getString("workflow_nome"));
		
		banco.setBanco_id(rsBancoProduto.getLong("banco_id"));
		banco.setNome(rsBancoProduto.getString("banco_nome"));
		
		produto.setProduto_id(rsBancoProduto.getLong("produto_id"));
		produto.setNome(rsBancoProduto.getString("produto_nome"));
		

		bancoProduto.setEmpresa(empresa);
		bancoProduto.setOrganizacao(organizacao);
		bancoProduto.setWorkflow(workflow);
		bancoProduto.setBanco(banco);
		bancoProduto.setProduto(produto);

		bancoProdutos.add(bancoProduto);

	}
	
}