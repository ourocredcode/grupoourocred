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

import br.com.sgo.modelo.CategoriaParceiro;
import br.com.sgo.modelo.ClassificacaoParceiro;
import br.com.sgo.modelo.GrupoParceiro;
import br.com.sgo.modelo.Janela;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoParceiro;

@Component
public class TipoParceiroDao extends Dao<TipoParceiro> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoParceiro;
	
	public TipoParceiroDao(Session session,ConnJDBC conexao) {
		super(session, TipoParceiro.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<TipoParceiro> buscaTipoParceiro(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select TIPOPARCEIRO.tipoparceiro_id, TIPOPARCEIRO.nome from TIPOPARCEIRO (NOLOCK) WHERE TIPOPARCEIRO.empresa_id = ? AND TIPOPARCEIRO.organizacao_id = ? AND TIPOPARCEIRO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoParceiro> tiposparceiro = new ArrayList<TipoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsTipoParceiro = this.stmt.executeQuery();

			while (rsTipoParceiro.next()) {
				TipoParceiro tipoparceiro = new TipoParceiro();

				tipoparceiro.setTipoParceiro_id(rsTipoParceiro.getLong("tipoparceiro_id"));				
				tipoparceiro.setNome(rsTipoParceiro.getString("nome"));

				tiposparceiro.add(tipoparceiro);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoParceiro, stmt, conn);

		return tiposparceiro;

	}

}