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
import br.com.sgo.modelo.OrganizacaoLocalidade;
import br.com.sgo.modelo.TipoEndereco;
import br.com.sgo.modelo.TipoLocalidade;

@Component
public class OrganizacaoLocalidadeDao extends Dao<OrganizacaoLocalidade> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOrganizacaoLocalidade;

	private String sqlOrganizacaoLocalidade = "SELECT ORGANIZACAOLOCALIDADE.organizacaolocalidade_id, ORGANIZACAOLOCALIDADE.empresa_id, ORGANIZACAOLOCALIDADE.organizacao_id " +
			", ORGANIZACAOLOCALIDADE.localidade_id, ORGANIZACAOLOCALIDADE.tipoendereco_id, ORGANIZACAOLOCALIDADE.isactive, ORGANIZACAOLOCALIDADE.numero, ORGANIZACAOLOCALIDADE.complemento " +
			", ORGANIZACAOLOCALIDADE.pontoreferencia FROM ORGANIZACAOLOCALIDADE ";

	private String sqlOrganizacaoLocalidades = "SELECT ORGANIZACAOLOCALIDADE.empresa_id, EMPRESA.nome AS empresa_nome, ORGANIZACAOLOCALIDADE.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
										", ORGANIZACAOLOCALIDADE.localidade_id, LOCALIDADE.endereco, LOCALIDADE.bairro, LOCALIDADE.cep, LOCALIDADE.nomeregiao "+
										", PAIS.nome AS pais_nome, CIDADE.nome AS cidade_nome, TIPOLOCALIDADE.nome tipolocalidade_nome, REGIAO.nome AS regiao_nome "+
										" FROM (((((((ORGANIZACAOLOCALIDADE "+
										" INNER JOIN EMPRESA (NOLOCK) ON ORGANIZACAOLOCALIDADE.empresa_id = EMPRESA.empresa_id) "+
										" INNER JOIN ORGANIZACAO (NOLOCK) ON ORGANIZACAOLOCALIDADE.organizacao_id = ORGANIZACAO.organizacao_id) "+ 
										" INNER JOIN LOCALIDADE (NOLOCK) ON ORGANIZACAOLOCALIDADE.localidade_id = LOCALIDADE.localidade_id) "+
										" INNER JOIN TIPOENDERECO (NOLOCK) ON ORGANIZACAOLOCALIDADE.tipoendereco_id = TIPOENDERECO.tipoendereco_id) "+ 
										" INNER JOIN PAIS (NOLOCK) ON LOCALIDADE.pais_id = PAIS.pais_id) "+
										" INNER JOIN CIDADE (NOLOCK) ON LOCALIDADE.cidade_id = CIDADE.cidade_id) "+
										" INNER JOIN REGIAO (NOLOCK) ON LOCALIDADE.regiao_id = REGIAO.regiao_id) "+
										" INNER JOIN TIPOLOCALIDADE (NOLOCK) ON LOCALIDADE.tipolocalidade_id = TIPOLOCALIDADE.tipolocalidade_id "; 

	public OrganizacaoLocalidadeDao(Session session, ConnJDBC conexao) {

		super(session, OrganizacaoLocalidade.class);
		this.conexao = conexao;

	}

	public Collection<OrganizacaoLocalidade> buscaOrganizacaoLocalidades(Long parceironegocio_id) {

		String sql = sqlOrganizacaoLocalidades;

		if (parceironegocio_id != null)
			sql += " WHERE ORGANIZACAOLOCALIDADE.organizacao_id = ? AND ORGANIZACAOLOCALIDADE.isactive = 1 ";

		this.conn = this.conexao.getConexao();

		Collection<OrganizacaoLocalidade> organizacaoLocalidades = new ArrayList<OrganizacaoLocalidade>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceironegocio_id);

			this.rsOrganizacaoLocalidade = this.stmt.executeQuery();

			while (rsOrganizacaoLocalidade.next()) {
				
				getOrganizacaoLocalidade(organizacaoLocalidades);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoLocalidade, stmt, conn);
		return organizacaoLocalidades;
	}

	public OrganizacaoLocalidade buscaOrganizacaoLocalidade(Long empresa_id,Long organizacao_id, Long localidade_id,Long tipoendereco_id) {

		String sql = sqlOrganizacaoLocalidade;

		if (empresa_id != null)
			sql += " WHERE ORGANIZACAOLOCALIDADE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.organizacao_id = ? ";
		if (localidade_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.localidade_id = ? ";
		if (tipoendereco_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.tipoendereco_id = ? ";

		this.conn = this.conexao.getConexao();

		OrganizacaoLocalidade organizacaoLocalidade = new OrganizacaoLocalidade();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, organizacaoLocalidade.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, organizacaoLocalidade.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, organizacaoLocalidade.getLocalidade().getLocalidade_id());
			this.stmt.setLong(4, organizacaoLocalidade.getTipoEndereco().getTipoEndereco_id());

			this.rsOrganizacaoLocalidade = this.stmt.executeQuery();

			while (rsOrganizacaoLocalidade.next()) {

				organizacaoLocalidade.setOrganizacaoLocalidade_id(rsOrganizacaoLocalidade.getLong("organizacaolocalidade_id"));

			}


		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoLocalidade, stmt, conn);
		return organizacaoLocalidade;
	}

	public OrganizacaoLocalidade buscaOrganizacaoLocalidadeNum(Long empresa_id,Long organizacao_id, Long localidade_id, Long tipoendereco_id, String numero) {

		String sql = sqlOrganizacaoLocalidade;

		if (empresa_id != null)
			sql += " WHERE ORGANIZACAOLOCALIDADE.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.organizacao_id = ? ";
		if (localidade_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.localidade_id = ? ";
		if (tipoendereco_id != null)
			sql += " AND ORGANIZACAOLOCALIDADE.tipoendereco_id = ? ";
		if (!numero.equals(""))
			sql += " AND ORGANIZACAOLOCALIDADE.numero = ? ";

		this.conn = this.conexao.getConexao();

		OrganizacaoLocalidade organizacaoLocalidade = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, localidade_id);
			this.stmt.setLong(4, tipoendereco_id);
			this.stmt.setString(5, numero);

			this.rsOrganizacaoLocalidade = this.stmt.executeQuery();

			while (rsOrganizacaoLocalidade.next()) {

				organizacaoLocalidade = new OrganizacaoLocalidade();
				organizacaoLocalidade.setOrganizacaoLocalidade_id(rsOrganizacaoLocalidade.getLong("organizacaolocalidade_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOrganizacaoLocalidade, stmt, conn);

		return organizacaoLocalidade;

	}

	public OrganizacaoLocalidade buscaOrganizacaoLocalidadeById(Long parceirolocalidade_id) {

		String sql = sqlOrganizacaoLocalidade;

		if (parceirolocalidade_id != null)
			sql += " WHERE ORGANIZACAOLOCALIDADE.parceirolocalidade_id = ? ";

		this.conn = this.conexao.getConexao();

		OrganizacaoLocalidade organizacaoLocalidade = new OrganizacaoLocalidade();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, organizacaoLocalidade.getOrganizacaoLocalidade_id());

			this.rsOrganizacaoLocalidade = this.stmt.executeQuery();
		
			while (rsOrganizacaoLocalidade.next()) {

				organizacaoLocalidade.setOrganizacaoLocalidade_id(rsOrganizacaoLocalidade.getLong("organizacaolocalidade_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoLocalidade, stmt, conn);
		return organizacaoLocalidade;
	}

	private void getOrganizacaoLocalidade(Collection<OrganizacaoLocalidade> organizacaoLocalidades)throws SQLException {

		Cidade c = new Cidade();
		TipoEndereco tipoEndereco = new TipoEndereco();
		OrganizacaoLocalidade organizacaoLocalidade = new OrganizacaoLocalidade();
		Localidade localidade = new Localidade();
		TipoLocalidade tipoLocalidade = new TipoLocalidade();

		c.setCidade_id(rsOrganizacaoLocalidade.getLong("cidade_id"));
		c.setNome(rsOrganizacaoLocalidade.getString("cidade_nome"));
		
		localidade.setCep(rsOrganizacaoLocalidade.getString("cep"));
		localidade.setLocalidade_id(rsOrganizacaoLocalidade.getLong("localidade_id"));
		localidade.setBairro(rsOrganizacaoLocalidade.getString("bairro"));
		localidade.setEndereco(rsOrganizacaoLocalidade.getString("endereco"));
		
		tipoLocalidade.setTipoLocalidade_id(rsOrganizacaoLocalidade.getLong("tipolocalidade_id"));
		tipoLocalidade.setNome(rsOrganizacaoLocalidade.getString("tipolocalidade_nome"));

		localidade.setCidade(c);
		localidade.setTipoLocalidade(tipoLocalidade);
		
		tipoEndereco.setTipoEndereco_id(rsOrganizacaoLocalidade.getLong("tipoendereco_id"));
		tipoEndereco.setNome(rsOrganizacaoLocalidade.getString("tipoendereco_nome"));

		organizacaoLocalidade.setOrganizacaoLocalidade_id(rsOrganizacaoLocalidade.getLong("organizacaolocalidade_id"));

		organizacaoLocalidade.setNumero(rsOrganizacaoLocalidade.getString("numero"));
		organizacaoLocalidade.setComplemento(rsOrganizacaoLocalidade.getString("complemento"));
		organizacaoLocalidade.setPontoReferencia(rsOrganizacaoLocalidade.getString("pontoreferencia"));

		organizacaoLocalidade.setLocalidade(localidade);
		organizacaoLocalidade.setTipoEndereco(tipoEndereco);

		organizacaoLocalidades.add(organizacaoLocalidade);

	}

}