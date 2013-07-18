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
import br.com.sgo.modelo.TipoEndereco;
import br.com.sgo.modelo.TipoLocalidade;

@Component
public class ParceiroLocalidadeDao extends Dao<ParceiroLocalidade> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroLocalidade;
	private String sqlParceiroLocalidade = "SELECT PARCEIROLOCALIDADE.parceirolocalidade_id, PARCEIROLOCALIDADE.empresa_id, PARCEIROLOCALIDADE.organizacao_id"
			+ ", PARCEIROLOCALIDADE.parceironegocio_id, PARCEIROLOCALIDADE.localidade_id, PARCEIROLOCALIDADE.tipoendereco_id, PARCEIROLOCALIDADE.numero, PARCEIROLOCALIDADE.complemento"
			+ ", PARCEIROLOCALIDADE.pontoreferencia FROM PARCEIROLOCALIDADE(NOLOCK)";

	private String sqlParceiroLocalidades = " SELECT PARCEIROLOCALIDADE.parceirolocalidade_id, PARCEIROLOCALIDADE.parceironegocio_id , LOCALIDADE.localidade_id, " + 
			" TIPOLOCALIDADE.tipolocalidade_id, TIPOLOCALIDADE.nome as tipolocalidade_nome,LOCALIDADE.endereco, LOCALIDADE.bairro, LOCALIDADE.cep, PARCEIROLOCALIDADE.numero , PARCEIROLOCALIDADE.complemento,PARCEIROLOCALIDADE.pontoreferencia, " + 
			" LOCALIDADE.cidade_id, CIDADE.nome as cidade_nome, LOCALIDADE.regiao_id, REGIAO.nome as regiao_nome , " + 
			" LOCALIDADE.pais_id, PAIS.nome as pais_nome, PARCEIROLOCALIDADE.tipoendereco_id, TIPOENDERECO.tipoendereco_id , " + 
			" TIPOENDERECO.nome as tipoendereco_nome FROM " + 
			" ((((LOCALIDADE INNER JOIN PAIS ON LOCALIDADE.pais_id = PAIS.pais_id) INNER JOIN REGIAO ON LOCALIDADE.regiao_id = REGIAO.regiao_id) " + 
			" INNER JOIN CIDADE ON LOCALIDADE.cidade_id = CIDADE.cidade_id) " + 
			" INNER JOIN (((ORGANIZACAO INNER JOIN PARCEIRONEGOCIO ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id) " + 
			" INNER JOIN EMPRESA ON PARCEIRONEGOCIO.empresa_id = EMPRESA.empresa_id) " + 
			" INNER JOIN PARCEIROLOCALIDADE ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROLOCALIDADE.parceironegocio_id) ON LOCALIDADE.localidade_id = PARCEIROLOCALIDADE.localidade_id) " + 
			" INNER JOIN TIPOENDERECO ON PARCEIROLOCALIDADE.tipoendereco_id = TIPOENDERECO.tipoendereco_id " +
			" INNER JOIN TIPOLOCALIDADE ON TIPOLOCALIDADE.tipolocalidade_id = LOCALIDADE.tipolocalidade_id "; 

	public ParceiroLocalidadeDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroLocalidade.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroLocalidade> buscaParceiroLocalidades(
			Long parceironegocio_id) {

		String sql = sqlParceiroLocalidades;

		if (parceironegocio_id != null)
			sql += " WHERE PARCEIROLOCALIDADE.parceironegocio_id = ? AND PARCEIROLOCALIDADE.isactive = 1 ";

		this.conn = this.conexao.getConexao();
		Collection<ParceiroLocalidade> parceiroLocalidades = new ArrayList<ParceiroLocalidade>();
		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceironegocio_id);

			this.rsParceiroLocalidade = this.stmt.executeQuery();

			while (rsParceiroLocalidade.next()) {

				Cidade c = new Cidade();
				c.setCidade_id(rsParceiroLocalidade.getLong("cidade_id"));
				c.setNome(rsParceiroLocalidade.getString("cidade_nome"));

				Localidade localidade = new Localidade();
				TipoLocalidade tipoLocalidade = new TipoLocalidade();
				localidade.setCep(rsParceiroLocalidade.getString("cep"));
				localidade.setLocalidade_id(rsParceiroLocalidade.getLong("localidade_id"));
				localidade.setBairro(rsParceiroLocalidade.getString("bairro"));
				localidade.setEndereco(rsParceiroLocalidade.getString("endereco"));
				tipoLocalidade.setTipoLocalidade_id(rsParceiroLocalidade.getLong("tipolocalidade_id"));
				tipoLocalidade.setNome(rsParceiroLocalidade.getString("tipolocalidade_nome"));

				localidade.setCidade(c);
				localidade.setTipoLocalidade(tipoLocalidade);

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

	public ParceiroLocalidade buscaParceiroLocalidade(Long empresa_id,
			Long organizacao_id, Long parceironegocio_id, Long localidade_id,
			Long tipoendereco_id) {

		String sql = sqlParceiroLocalidade;

		if (empresa_id != null)
			sql += " WHERE PARCEIROLOCALIDADE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND PARCEIROLOCALIDADE.organizacao_id = ? ";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROLOCALIDADE.parceironegocio_id = ? ";
		if (localidade_id != null)
			sql += " AND PARCEIROLOCALIDADE.localidade_id = ? ";
		if (tipoendereco_id != null)
			sql += " AND PARCEIROLOCALIDADE.tipoendereco_id = ? ";

		this.conn = this.conexao.getConexao();
		ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceiroLocalidade.getEmpresa()
					.getEmpresa_id());
			this.stmt.setLong(2, parceiroLocalidade.getOrganizacao()
					.getOrganizacao_id());
			this.stmt.setLong(3, parceiroLocalidade.getParceiroNegocio()
					.getParceiroNegocio_id());
			this.stmt.setLong(4, parceiroLocalidade.getLocalidade()
					.getLocalidade_id());
			this.stmt.setLong(5, parceiroLocalidade.getTipoEndereco()
					.getTipoEndereco_id());

			this.rsParceiroLocalidade = this.stmt.executeQuery();

			while (rsParceiroLocalidade.next()) {
				parceiroLocalidade
						.setParceiroLocalidade_id(rsParceiroLocalidade
								.getLong("parceirolocalidade_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);
		return parceiroLocalidade;
	}

	public ParceiroLocalidade buscaParceiroLocalidadeNum(Long empresa_id,
			Long organizacao_id, Long parceironegocio_id, Long localidade_id,
			Long tipoendereco_id, String numero) {

		String sql = sqlParceiroLocalidade;

		if (empresa_id != null)
			sql += " WHERE PARCEIROLOCALIDADE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND PARCEIROLOCALIDADE.organizacao_id = ? ";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROLOCALIDADE.parceironegocio_id = ? ";
		if (localidade_id != null)
			sql += " AND PARCEIROLOCALIDADE.localidade_id = ? ";
		if (tipoendereco_id != null)
			sql += " AND PARCEIROLOCALIDADE.tipoendereco_id = ? ";
		if (!numero.equals(""))
			sql += " AND PARCEIROLOCALIDADE.numero = ? ";

		this.conn = this.conexao.getConexao();
		ParceiroLocalidade parceiroLocalidade = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
			this.stmt.setLong(4, localidade_id);
			this.stmt.setLong(5, tipoendereco_id);
			this.stmt.setString(6, numero);

			this.rsParceiroLocalidade = this.stmt.executeQuery();

			while (rsParceiroLocalidade.next()) {

				parceiroLocalidade = new ParceiroLocalidade();
				parceiroLocalidade
						.setParceiroLocalidade_id(rsParceiroLocalidade
								.getLong("parceirolocalidade_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);

		return parceiroLocalidade;

	}

	public ParceiroLocalidade buscaParceiroLocalidadeById(
			Long parceirolocalidade_id) {

		String sql = sqlParceiroLocalidade;

		if (parceirolocalidade_id != null)
			sql += " WHERE PARCEIROLOCALIDADE.parceirolocalidade_id = ? ";

		this.conn = this.conexao.getConexao();
		ParceiroLocalidade parceiroLocalidade = new ParceiroLocalidade();
		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceiroLocalidade.getParceiroLocalidade_id());
			this.rsParceiroLocalidade = this.stmt.executeQuery();
			while (rsParceiroLocalidade.next()) {
				parceiroLocalidade
						.setParceiroLocalidade_id(rsParceiroLocalidade
								.getLong("parceirolocalidade_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroLocalidade, stmt, conn);
		return parceiroLocalidade;
	}

}