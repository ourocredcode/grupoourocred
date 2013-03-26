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
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.TipoEndereco;

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
		
		String sql = "SELECT PARCEIROLOCALIDADE.parceirolocalidade_id, PARCEIROLOCALIDADE.parceironegocio_id , LOCALIDADE.localidade_id, " +
				"		LOCALIDADE.endereco, LOCALIDADE.bairro, LOCALIDADE.cep, PARCEIROLOCALIDADE.numero , PARCEIROLOCALIDADE.complemento,PARCEIROLOCALIDADE.pontoreferencia, " +
				"		LOCALIDADE.cidade_id, CIDADE.nome as cidade_nome, LOCALIDADE.regiao_id, REGIAO.nome as regiao_nome , " +
				"		LOCALIDADE.pais_id, PAIS.nome as pais_nome, PARCEIROLOCALIDADE.tipoendereco_id, TIPOENDERECO.tipoendereco_id , " +
				"		TIPOENDERECO.nome as tipoendereco_nome FROM " +
				"		((((LOCALIDADE INNER JOIN PAIS ON LOCALIDADE.pais_id = PAIS.pais_id) INNER JOIN REGIAO ON LOCALIDADE.regiao_id = REGIAO.regiao_id) " +
				"			INNER JOIN CIDADE ON LOCALIDADE.cidade_id = CIDADE.cidade_id) " +
				"			INNER JOIN (((ORGANIZACAO INNER JOIN PARCEIRONEGOCIO ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id) " +
				"			INNER JOIN EMPRESA ON PARCEIRONEGOCIO.empresa_id = EMPRESA.empresa_id) " +
				"			INNER JOIN PARCEIROLOCALIDADE ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROLOCALIDADE.parceironegocio_id) ON " +
				"				LOCALIDADE.localidade_id = PARCEIROLOCALIDADE.localidade_id) " +
				"			INNER JOIN TIPOENDERECO ON PARCEIROLOCALIDADE.tipoendereco_id = TIPOENDERECO.tipoendereco_id " +
				"		WHERE PARCEIROLOCALIDADE.isactive = 1 AND PARCEIROLOCALIDADE.parceironegocio_id = ? ";

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
				
				TipoEndereco tipoEndereco = new TipoEndereco();
				tipoEndereco.setTipoEndereco_id(rsParceiroLocalidade.getLong("tipoendereco_id"));
				tipoEndereco.setNome(rsParceiroLocalidade.getString("tipoendereco_nome"));

				ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();
				parceiroLocalidade.setParceiroLocalidade_id(rsParceiroLocalidade.getLong("parceirolocalidade_id"));			

				parceiroLocalidade.setNumero(rsParceiroLocalidade.getString("numero"));
				parceiroLocalidade.setComplemento(rsParceiroLocalidade.getString("complemento"));
				parceiroLocalidade.setPontoReferencia(rsParceiroLocalidade.getString("pontoreferencia"));

				parceiroLocalidade.setLocalidade(localidade);
				parceiroLocalidade.setTipoEndereco(tipoEndereco);

				parceiroLocalidades.add(parceiroLocalidade);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);
		return parceiroLocalidades;
	}
	
	public ParceiroLocalidade buscaParceiroLocalidade(ParceiroNegocio parceiroNegocio,Localidade localidade, TipoEndereco tipoEndereco){
		
		String sql = "SELECT PARCEIROLOCALIDADE.parceirolocalidade_id " +
				"	FROM PARCEIROLOCALIDADE WHERE parceironegocio_id = ? AND localidade_id = ? AND tipoendereco_id = ? AND isactive = 1 ";

		this.conn = this.conexao.getConexao();

		ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1,parceiroNegocio.getParceiroNegocio_id());	
			this.stmt.setLong(2,localidade.getLocalidade_id());
			this.stmt.setLong(3,tipoEndereco.getTipoEndereco_id());

			this.rsParceiroLocalidade = this.stmt.executeQuery();

			while (rsParceiroLocalidade.next()) {
				parceiroLocalidade.setParceiroLocalidade_id(rsParceiroLocalidade.getLong("parceirolocalidade_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);
		return parceiroLocalidade;
	}

}