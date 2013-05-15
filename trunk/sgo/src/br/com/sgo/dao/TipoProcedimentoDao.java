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
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoProcedimento;

@Component
public class TipoProcedimentoDao extends Dao<TipoProcedimento> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoProcedimento;

	private final String sqlTipoProcedimento = "SELECT TIPOPROCEDIMENTO.tipoprocedimento_id, TIPOPROCEDIMENTO.nome as tipoprocedimento_nome, "+ 
								" TIPOPROCEDIMENTO.empresa_id, EMPRESA.nome as empresa_nome, TIPOPROCEDIMENTO.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
								" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
								" INNER JOIN TIPOPROCEDIMENTO (NOLOCK) ON EMPRESA.empresa_id = TIPOPROCEDIMENTO.empresa_id) ON ORGANIZACAO.organizacao_id = TIPOPROCEDIMENTO.organizacao_id ";

	public TipoProcedimentoDao(Session session, ConnJDBC conexao) {

		super(session, TipoProcedimento.class);
		this.conexao = conexao;

	}

	public Collection<TipoProcedimento> buscaAllTipoProcedimento(Long empresa_id, Long organizacao_id) {

		String sql = sqlTipoProcedimento;
		
		if (empresa_id != null)
			sql += " WHERE TIPOPROCEDIMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOPROCEDIMENTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoProcedimento> tipoProcedimentos = new ArrayList<TipoProcedimento>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);				
			this.stmt.setLong(2, organizacao_id);

			this.rsTipoProcedimento = this.stmt.executeQuery();

			while (rsTipoProcedimento.next()) {

				getTipoProcedimentos(tipoProcedimentos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoProcedimento, stmt, conn);
		return tipoProcedimentos;
	}
	
	public Collection<TipoProcedimento> buscaTipoProcedimentosPorNome(String nome) {
		
		String sql = sqlTipoProcedimento;
	
		if (nome != null)
			sql += " AND TIPOPROCEDIMENTO.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<TipoProcedimento> tipoProcedimentos = new ArrayList<TipoProcedimento>();

		if (tipoProcedimentos!=null){

			try {

				this.stmt = conn.prepareStatement(sql);
				
				this.stmt.setString(1, "%" + nome + "%");

				this.rsTipoProcedimento= this.stmt.executeQuery();

				while (rsTipoProcedimento.next()) {

					getTipoProcedimentos(tipoProcedimentos);

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			this.conexao.closeConnection(rsTipoProcedimento, stmt, conn);

		}
		return tipoProcedimentos;
	}

	public TipoProcedimento buscaTipoProcedimentosByEmOrNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlTipoProcedimento;

		if (empresa_id != null)
			sql += " WHERE TIPOPROCEDIMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOPROCEDIMENTO.organizacao_id = ?";
		if (nome != null)
			sql += " AND TIPOPROCEDIMENTO.nome like ?";

		this.conn = this.conexao.getConexao();

		TipoProcedimento tipoProcedimento = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);		
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoProcedimento = this.stmt.executeQuery();

			while (rsTipoProcedimento.next()) {

				tipoProcedimento = new TipoProcedimento();
				tipoProcedimento.setTipoProcedimento_id(rsTipoProcedimento.getLong("tipoprocedimento_id"));
				tipoProcedimento.setNome(rsTipoProcedimento.getString("tipoprocedimento_nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoProcedimento, stmt, conn);
		return tipoProcedimento;
	}

	private void getTipoProcedimentos(Collection<TipoProcedimento> tipoProcedimentos) throws SQLException {

		TipoProcedimento tipoProcedimento = new TipoProcedimento();
		Empresa e = new Empresa();
		Organizacao o = new Organizacao();

		e.setEmpresa_id(rsTipoProcedimento.getLong("empresa_id"));
		e.setNome(rsTipoProcedimento.getString("empresa_nome"));

		o.setOrganizacao_id(rsTipoProcedimento.getLong("organizacao_id"));
		o.setNome(rsTipoProcedimento.getString("organizacao_nome"));

		tipoProcedimento.setTipoProcedimento_id(rsTipoProcedimento.getLong("tipoprocedimento_id"));
		tipoProcedimento.setNome(rsTipoProcedimento.getString("tipoprocedimento_nome"));

		tipoProcedimento.setEmpresa(e);
		tipoProcedimento.setOrganizacao(o);

		tipoProcedimentos.add(tipoProcedimento);

	}

}