package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Cidade;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Pais;
import br.com.sgo.modelo.Regiao;

@Component
public class LocalidadeDao extends Dao<Localidade> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsLocalidades;

	private String sqlLocalidade = "SELECT LOCALIDADE.empresa_id, "
			+ "			EMPRESA.nome as empresa_nome, LOCALIDADE.organizacao_id, ORGANIZACAO.nome as organizacao_nome, LOCALIDADE.pais_id, PAIS.nome as pais_nome, "
			+ "			LOCALIDADE.cidade_id, CIDADE.nome as cidade_nome, LOCALIDADE.localidade_id, LOCALIDADE.regiao_id, REGIAO.chave as regiao_chave, LOCALIDADE.tipolocalidade_id, "
			+ "			TIPOLOCALIDADE.nome, LOCALIDADE.endereco, LOCALIDADE.bairro, LOCALIDADE.cep "
			+ "	  FROM ((((( LOCALIDADE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON LOCALIDADE.empresa_id = EMPRESA.empresa_id) "
			+ "				INNER JOIN ORGANIZACAO (NOLOCK) ON LOCALIDADE.organizacao_id = ORGANIZACAO.organizacao_id) "
			+ "				INNER JOIN REGIAO (NOLOCK) ON LOCALIDADE.regiao_id = REGIAO.regiao_id) "
			+ "				INNER JOIN CIDADE (NOLOCK) ON LOCALIDADE.cidade_id = CIDADE.cidade_id) "
			+ "				INNER JOIN PAIS (NOLOCK) ON LOCALIDADE.pais_id = PAIS.pais_id) "
			+ "				INNER JOIN TIPOLOCALIDADE (NOLOCK) ON LOCALIDADE.tipolocalidade_id = TIPOLOCALIDADE.tipolocalidade_id ";

	public LocalidadeDao(Session session, ConnJDBC conexao) {
		super(session, Localidade.class);
		this.conexao = conexao;
	}

	public Localidade buscaLocalidade(String cep) {
		
		String sql = sqlLocalidade;

		if (!cep.equals("")) {
			sql += " WHERE LOCALIDADE.cep like ? ";
		}

		this.conn = this.conexao.getConexao();

		Localidade localidade = new Localidade();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1,"%" + cep + "%");
			
			System.out.println(sql);
			System.out.println(cep);

			this.rsLocalidades = this.stmt.executeQuery();

			while (rsLocalidades.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsLocalidades.getLong("empresa_id"));
				empresa.setNome(rsLocalidades.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsLocalidades.getLong("organizacao_id"));
				organizacao.setNome(rsLocalidades.getString("organizacao_nome"));

				Pais pais = new Pais();
				pais.setPais_id(rsLocalidades.getLong("pais_id"));
				pais.setNome(rsLocalidades.getString("pais_nome"));

				Regiao regiao = new Regiao();
				regiao.setRegiao_id(rsLocalidades.getLong("regiao_id"));
				regiao.setChave(rsLocalidades.getString("regiao_chave"));

				Cidade cidade = new Cidade();
				cidade.setCidade_id(rsLocalidades.getLong("cidade_id"));
				cidade.setNome(rsLocalidades.getString("cidade_nome"));

				localidade.setLocalidade_id(rsLocalidades.getLong("localidade_id"));
				localidade.setEndereco(rsLocalidades.getString("endereco"));
				localidade.setBairro(rsLocalidades.getString("bairro"));
				localidade.setCep(rsLocalidades.getString("cep"));
				localidade.setCidade(cidade);
				localidade.setRegiao(regiao);
				localidade.setPais(pais);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsLocalidades, stmt, conn);

		return localidade;

	}

}