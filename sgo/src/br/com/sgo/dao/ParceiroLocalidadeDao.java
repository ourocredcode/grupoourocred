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
import br.com.sgo.modelo.Cidade;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.ParceiroLocalidade;

@Component
public class ParceiroLocalidadeDao extends Dao<ParceiroLocalidade> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroLocalidade;

	public ParceiroLocalidadeDao(Session session,ConnJDBC conexao) {
		super(session, ParceiroLocalidade.class);
		this.conexao = conexao;
	}
	
	public Collection<ParceiroLocalidade> buscaParceiroLocalidades(Long parceironegocio_id){

		String sql = "SELECT PARCEIROLOCALIDADE.parceirolocalidade_id, PARCEIROLOCALIDADE.parceironegocio_id, " +
				"		LOCALIDADE.localidade_id, LOCALIDADE.endereco, LOCALIDADE.bairro, LOCALIDADE.cep, PARCEIROLOCALIDADE.numero, " +
				"		PARCEIROLOCALIDADE.complemento, LOCALIDADE.cidade_id, CIDADE.nome as cidade_nome, LOCALIDADE.regiao_id, REGIAO.nome, " +
				"		LOCALIDADE.pais_id, PAIS.nome, PARCEIROLOCALIDADE.isentrega, PARCEIROLOCALIDADE.iscobranca, PARCEIROLOCALIDADE.isfatura, " +
				"		PARCEIROLOCALIDADE.isassinatura, PARCEIROLOCALIDADE.isresidencial FROM ((((((ORGANIZACAO (NOLOCK) " +
				"			INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id) " +
				"			INNER JOIN PARCEIROLOCALIDADE ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROLOCALIDADE.parceironegocio_id) " +
				"			INNER JOIN LOCALIDADE (NOLOCK) ON PARCEIROLOCALIDADE.localidade_id = LOCALIDADE.localidade_id) " +
				"			INNER JOIN EMPRESA (NOLOCK) ON PARCEIRONEGOCIO.empresa_id = EMPRESA.empresa_id) " +
				"			INNER JOIN PAIS (NOLOCK) ON LOCALIDADE.pais_id = PAIS.pais_id) " +
				"			INNER JOIN REGIAO ON LOCALIDADE.regiao_id = REGIAO.regiao_id) " +
				"			INNER JOIN CIDADE (NOLOCK) ON LOCALIDADE.cidade_id = CIDADE.cidade_id WHERE PARCEIRONEGOCIO.parceironegocio_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroLocalidade> parceiroLocalidades = new ArrayList<ParceiroLocalidade>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1,parceironegocio_id);			

			this.rsParceiroLocalidade = this.stmt.executeQuery();

			while (rsParceiroLocalidade.next()) {

				Cidade c = new Cidade();
				c.setCidade_id(rsParceiroLocalidade.getLong("cidade_id"));
				c.setNome(rsParceiroLocalidade.getString("cidade_nome"));

				Localidade localidade = new Localidade();
				localidade.setCep(rsParceiroLocalidade.getString("cep"));
				localidade.setLocalidade_id(rsParceiroLocalidade.getLong("localidade_id"));
				localidade.setBairro(rsParceiroLocalidade.getString("bairro"));
				localidade.setEndereco(rsParceiroLocalidade.getString("endereco"));

				localidade.setCidade(c);

				ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();
				parceiroLocalidade.setParceiroLocalidade_id(rsParceiroLocalidade.getLong("parceirolocalidade_id"));			

				parceiroLocalidade.setNumero(rsParceiroLocalidade.getString("numero"));
				parceiroLocalidade.setComplemento(rsParceiroLocalidade.getString("complemento"));
				
				parceiroLocalidade.setIsAssinatura(rsParceiroLocalidade.getBoolean("isassinatura"));
				parceiroLocalidade.setIsResidencial(rsParceiroLocalidade.getBoolean("isresidencial"));
				
				parceiroLocalidade.setLocalidade(localidade);

				parceiroLocalidades.add(parceiroLocalidade);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);
		return parceiroLocalidades;
	}

}