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
import br.com.sgo.modelo.TabelaBd;

@Component
public class TabelaBdDao extends Dao<TabelaBd> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTabelas;

	public TabelaBdDao(Session session, ConnJDBC conexao) {
		super(session, TabelaBd.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<TabelaBd> buscaTabelas(Long empresa_id, Long organizacao_id, String nometabelabd){

		String sql = "select TABELABD.tabelabd_id, TABELABD.nometabelabd from TABELABD (NOLOCK) " +
				"	WHERE TABELABD.empresa_id = ? AND TABELABD.organizacao_id = ? AND TABELABD.nometabelabd like ? ";
		this.conn = this.conexao.getConexao();

		Collection<TabelaBd> tabelas = new ArrayList<TabelaBd>();
		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nometabelabd + "%");			
			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {
				TabelaBd tabela = new TabelaBd();

				tabela.setTabelaBd_id(rsTabelas.getLong("tabelabd_id"));				
				tabela.setNomeTabelaBd(rsTabelas.getString("nometabelabd"));

				tabelas.add(tabela);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTabelas, stmt, conn);

		return tabelas;

	}

}
