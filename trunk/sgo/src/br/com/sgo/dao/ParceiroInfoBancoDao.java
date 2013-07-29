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
import br.com.sgo.modelo.MeioPagamento;
import br.com.sgo.modelo.ParceiroInfoBanco;

@Component
public class ParceiroInfoBancoDao extends Dao<ParceiroInfoBanco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroInfoBanco;

	private final String sqlParceiroInfoBanco = " SELECT PARCEIROINFOBANCO.parceiroinfobanco_id, PARCEIROINFOBANCO.empresa_id, PARCEIROINFOBANCO.organizacao_id, PARCEIROINFOBANCO.parceironegocio_id, PARCEIROINFOBANCO.banco_id, PARCEIROINFOBANCO.agencia_id, PARCEIROINFOBANCO.contabancaria_id, PARCEIROINFOBANCO.meiopagamento_id FROM PARCEIROINFOBANCO (NOLOCK) ";

	private final String sqlParceiroInfoBancos =  " SELECT PARCEIROINFOBANCO.parceiroinfobanco_id, PARCEIROINFOBANCO.empresa_id, EMPRESA.nome AS empresa_nome "+
							", PARCEIROINFOBANCO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
							", PARCEIROINFOBANCO.parceironegocio_id, PARCEIRONEGOCIO.nome AS parceironegocio_nome "+
							", PARCEIROINFOBANCO.banco_id, BANCO.nome AS banco_nome, PARCEIROINFOBANCO.isactive "+
							", PARCEIROINFOBANCO.meiopagamento_id, MEIOPAGAMENTO.nome as meiopagamento_nome, PARCEIROINFOBANCO.contacorrente, PARCEIROINFOBANCO.agencianumero "+
							" FROM ((((PARCEIROINFOBANCO (NOLOCK) LEFT JOIN BANCO (NOLOCK) ON PARCEIROINFOBANCO.banco_id = BANCO.banco_id) "+
							" INNER JOIN EMPRESA (NOLOCK) ON PARCEIROINFOBANCO.empresa_id = EMPRESA.empresa_id) "+
							" INNER JOIN ORGANIZACAO (NOLOCK) ON PARCEIROINFOBANCO.organizacao_id = ORGANIZACAO.organizacao_id) "+
							" INNER JOIN MEIOPAGAMENTO (NOLOCK) ON PARCEIROINFOBANCO.meiopagamento_id = MEIOPAGAMENTO.meiopagamento_id) "+
							" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROINFOBANCO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";

	public ParceiroInfoBancoDao(Session session, ConnJDBC conexao) {

		super(session, ParceiroInfoBanco.class);
		this.conexao = conexao;

	}

	public Collection<ParceiroInfoBanco> buscaAllParceiroInfoBanco() {

		String sql = sqlParceiroInfoBancos;

		this.conn = this.conexao.getConexao();

		Collection<ParceiroInfoBanco> informacoesParceiro = new ArrayList<ParceiroInfoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsParceiroInfoBanco = this.stmt.executeQuery();

			while (rsParceiroInfoBanco.next()) {

				ParceiroInfoBanco informacaoParceiro = new ParceiroInfoBanco();
				informacaoParceiro.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));
				informacoesParceiro.add(informacaoParceiro);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);
		return informacoesParceiro;
	}

	public ParceiroInfoBanco buscaParceiroInfoBancoByParceiro(Long parceironegocio_id) {

		String sql = sqlParceiroInfoBancos;

		if (parceironegocio_id != null)
			sql += " WHERE PARCEIROINFOBANCO.parceironegocio_id = ?";

		this.conn = this.conexao.getConexao();

		ParceiroInfoBanco parceiroInfoBanco = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceironegocio_id);

			this.rsParceiroInfoBanco = this.stmt.executeQuery();

			while (rsParceiroInfoBanco.next()) {

				parceiroInfoBanco = new ParceiroInfoBanco();
				MeioPagamento meioPagamento = new MeioPagamento();
				Banco banco = new Banco();

				meioPagamento.setMeioPagamento_id(rsParceiroInfoBanco.getLong("meiopagamento_id"));
				meioPagamento.setNome(rsParceiroInfoBanco.getString("meiopagamento_nome"));

				banco.setBanco_id(rsParceiroInfoBanco.getLong("banco_id"));
				banco.setNome(rsParceiroInfoBanco.getString("banco_nome"));

				parceiroInfoBanco.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));
				parceiroInfoBanco.setMeioPagamento(meioPagamento);
				parceiroInfoBanco.setBanco(banco);
				parceiroInfoBanco.setContaCorrente(rsParceiroInfoBanco.getString("contacorrente"));
				parceiroInfoBanco.setAgenciaNumero(rsParceiroInfoBanco.getString("agencianumero"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);

		return parceiroInfoBanco;
	}

	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBa(Long empresa_id,Long organizacao_id, Long parceironegocio_id, Long banco_id) {

		String sql = sqlParceiroInfoBanco;

		if (empresa_id != null)
			sql += " WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PARCEIROINFOBANCO.organizacao_id = ?";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if (banco_id != null)
			sql += " AND PARCEIROINFOBANCO.banco_id = ?";

		this.conn = this.conexao.getConexao();

	ParceiroInfoBanco informacaoParceiro = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
			this.stmt.setLong(4, banco_id);

			this.rsParceiroInfoBanco = this.stmt.executeQuery();

			while (rsParceiroInfoBanco.next()) {

				informacaoParceiro = new ParceiroInfoBanco();
				informacaoParceiro.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);
		return informacaoParceiro;
	}

	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBaAg(Long empresa_id, Long organizacao_id, Long parceironegocio_id, Long banco_id, Long agencia_id) {

		String sql = sqlParceiroInfoBanco;

		if (empresa_id != null)
			sql += " WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PARCEIROINFOBANCO.organizacao_id = ?";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if (banco_id != null)
			sql += " AND PARCEIROINFOBANCO.banco_id = ?";
		if (agencia_id != null)
			sql += " AND PARCEIROINFOBANCO.agencia_id = ?";

		this.conn = this.conexao.getConexao();
		
		ParceiroInfoBanco informacaoParceiro = null;
		
		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, agencia_id);

			this.rsParceiroInfoBanco = this.stmt.executeQuery();

			while (rsParceiroInfoBanco.next()) {

				informacaoParceiro = new ParceiroInfoBanco();
				informacaoParceiro.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);
		return informacaoParceiro;
	}

	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBaAgCb(Long empresa_id, Long organizacao_id, Long parceironegocio_id,Long banco_id, Long agencia_id, Long contabancaria_id) {

		String sql = sqlParceiroInfoBanco;

		if (empresa_id != null)
			sql += " WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PARCEIROINFOBANCO.organizacao_id = ?";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if (banco_id != null)
			sql += " AND PARCEIROINFOBANCO.banco_id = ?";
		if (agencia_id != null)
			sql += " AND PARCEIROINFOBANCO.agencia_id = ?";
		if (contabancaria_id != null)
			sql += " AND PARCEIROINFOBANCO.contabancaria_id = ?";

		this.conn = this.conexao.getConexao();
		
		ParceiroInfoBanco informacaoParceiro = null;
		
		try {
		
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, agencia_id);
			this.stmt.setLong(6, contabancaria_id);
			
			this.rsParceiroInfoBanco = this.stmt.executeQuery();
			
			while (rsParceiroInfoBanco.next()) {
			
				informacaoParceiro = new ParceiroInfoBanco();
				informacaoParceiro.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);
		return informacaoParceiro;
	}

	public ParceiroInfoBanco buscaParceiroInfoBancoByAllTypes(Long empresa_id,
			Long organizacao_id, Long parceironegocio_id, Long banco_id,
			Long agencia_id, Long contabancaria_id, String nome) {

		String sql = sqlParceiroInfoBancos;

		if (empresa_id != null)
			sql += " WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PARCEIROINFOBANCO.organizacao_id = ?";
		if (parceironegocio_id != null)
			sql += " AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if (banco_id != null)
			sql += " AND PARCEIROINFOBANCO.banco_id = ?";
		if (agencia_id != null)
			sql += " AND PARCEIROINFOBANCO.agencia_id = ?";
		if (contabancaria_id != null)
			sql += " AND PARCEIROINFOBANCO.contabancaria_id = ?";
		if (nome != null)
			sql += " AND (PARCEIROINFOBANCO.nome like ?)";

		this.conn = this.conexao.getConexao();

		ParceiroInfoBanco informacaoParceiro = null;

		try {

			this.stmt = conn.prepareStatement(sql);


			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, agencia_id);
			this.stmt.setLong(6, contabancaria_id);
			this.stmt.setString(7, "%" + nome + "%");

			this.rsParceiroInfoBanco = this.stmt.executeQuery();

			while (rsParceiroInfoBanco.next()) {

				informacaoParceiro = new ParceiroInfoBanco();
				informacaoParceiro.setParceiroInfoBanco_id(rsParceiroInfoBanco.getLong("parceiroinfobanco_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroInfoBanco, stmt, conn);
		return informacaoParceiro;
	}
}