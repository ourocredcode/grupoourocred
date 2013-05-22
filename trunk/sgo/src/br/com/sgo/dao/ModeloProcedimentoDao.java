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
import br.com.sgo.modelo.ModeloProcedimento;
import br.com.sgo.modelo.Organizacao;

@Component
public class ModeloProcedimentoDao extends Dao<ModeloProcedimento> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsModeloProcedimento;
	
	private final String sqlModeloprocedimento = "SELECT MODELOPROCEDIMENTO.modeloprocedimento_id, MODELOPROCEDIMENTO.nome AS modeloprocedimento_nome "+
						", MODELOPROCEDIMENTO.empresa_id, EMPRESA.nome AS empresa_nome, MODELOPROCEDIMENTO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN MODELOPROCEDIMENTO ON EMPRESA.empresa_id = MODELOPROCEDIMENTO.empresa_id) ON ORGANIZACAO.organizacao_id = MODELOPROCEDIMENTO.organizacao_id ";

	public ModeloProcedimentoDao(Session session, ConnJDBC conexao) {
		super(session, ModeloProcedimento.class);
		this.conexao = conexao;
	}

	public Collection<ModeloProcedimento> buscaModeloProcedimentos(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlModeloprocedimento;

		this.conn = this.conexao.getConexao();

		Collection<ModeloProcedimento> modelosProcedimento = new ArrayList<ModeloProcedimento>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsModeloProcedimento = this.stmt.executeQuery();

			while (rsModeloProcedimento.next()) {

				getModeloProcedimento(modelosProcedimento);
			}	

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsModeloProcedimento, stmt, conn);

		return modelosProcedimento;

	}
	
	public ModeloProcedimento buscaModeloProcedimentoById(Long modeloprocedimento_id) {

		String sql = sqlModeloprocedimento;

		this.conn = this.conexao.getConexao();

		ModeloProcedimento modeloProcedimento = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, modeloprocedimento_id);

			this.rsModeloProcedimento = this.stmt.executeQuery();

			while (rsModeloProcedimento.next()) {
				
				modeloProcedimento = new ModeloProcedimento();

				modeloProcedimento.setModeloProcedimento_id(rsModeloProcedimento.getLong("modeloprocedimento_id"));
				modeloProcedimento.setNome(rsModeloProcedimento.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsModeloProcedimento, stmt, conn);

		return modeloProcedimento;

	}
	
	public Collection<ModeloProcedimento> buscaModeloProcedimentoByGrupo(String grupo){
		
		String sql = sqlModeloprocedimento;

		this.conn = this.conexao.getConexao();

		Collection<ModeloProcedimento> modelosProcedimento = new ArrayList<ModeloProcedimento>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, grupo);

			this.rsModeloProcedimento = this.stmt.executeQuery();

			while (rsModeloProcedimento.next()) {

				getModeloProcedimento(modelosProcedimento);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsModeloProcedimento, stmt, conn);

		return modelosProcedimento;

	}
	
	public Collection<ModeloProcedimento> buscaModeloProcedimentoByBanco(Long banco_id){
		
		String sql = "SELECT DISTINCT(PROCEDIMENTODETALHE.modeloprocedimento_id), MODELOPROCEDIMENTO.nome AS modeloprocedimento_nome, MODELOPROCEDIMENTO.descricao "+
						", PROCEDIMENTODETALHE.empresa_id, EMPRESA.nome AS empresa_nome, PROCEDIMENTODETALHE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM (MODELOPROCEDIMENTO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
						" INNER JOIN PROCEDIMENTODETALHE (NOLOCK) ON EMPRESA.empresa_id = PROCEDIMENTODETALHE.empresa_id) ON MODELOPROCEDIMENTO.modeloprocedimento_id = PROCEDIMENTODETALHE.modeloprocedimento_id) "+ 
						" INNER JOIN ORGANIZACAO (NOLOCK) ON PROCEDIMENTODETALHE.organizacao_id = ORGANIZACAO.organizacao_id ";
		
		if (banco_id != null)
			sql += " WHERE PROCEDIMENTODETALHE.banco_id = ?";
		
		this.conn = this.conexao.getConexao();

		Collection<ModeloProcedimento> modelosProcedimento = new ArrayList<ModeloProcedimento>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, banco_id);

			this.rsModeloProcedimento = this.stmt.executeQuery();

			while (rsModeloProcedimento.next()) {

				ModeloProcedimento modeloProcedimento = new ModeloProcedimento();

				modeloProcedimento.setModeloProcedimento_id(rsModeloProcedimento.getLong("modeloprocedimento_id"));
				modeloProcedimento.setNome(rsModeloProcedimento.getString("modeloprocedimento_nome"));
				modeloProcedimento.setDescricao(rsModeloProcedimento.getString("descricao"));

				modelosProcedimento.add(modeloProcedimento);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsModeloProcedimento, stmt, conn);

		return modelosProcedimento;

	}

	private void getModeloProcedimento(Collection<ModeloProcedimento> modelosProcedimento) throws SQLException {
		
		ModeloProcedimento modeloProcedimento = new ModeloProcedimento();
		
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		
		empresa.setEmpresa_id(rsModeloProcedimento.getLong("empresa_id"));
		empresa.setNome(rsModeloProcedimento.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsModeloProcedimento.getLong("organizacao_id"));
		organizacao.setNome(rsModeloProcedimento.getString("organizacao_nome"));
		
		modeloProcedimento.setEmpresa(empresa);
		modeloProcedimento.setOrganizacao(organizacao);	
		modeloProcedimento.setModeloProcedimento_id(rsModeloProcedimento.getLong("modeloprocedimento_id"));
		modeloProcedimento.setNome(rsModeloProcedimento.getString("nome"));
	
		modelosProcedimento.add(modeloProcedimento);
	}

}