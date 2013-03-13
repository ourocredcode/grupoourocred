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
import br.com.sgo.modelo.GrupoParceiro;
import br.com.sgo.modelo.Janela;
import br.com.sgo.modelo.Organizacao;

@Component
public class BancoDao extends Dao<Banco> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsBanco;
	
	public BancoDao(Session session,ConnJDBC conexao) {
		super(session, Banco.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<Banco> buscaBancos(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select BANCO.banco_id, BANCO.nome from BANCO (NOLOCK) WHERE BANCO.empresa_id = ? AND BANCO.organizacao_id = ? AND BANCO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Banco> bancos = new ArrayList<Banco>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsBanco = this.stmt.executeQuery();

			while (rsBanco.next()) {
				Banco banco = new Banco();

				banco.setBanco_id(rsBanco.getLong("banco_id"));				
				banco.setNome(rsBanco.getString("nome"));

				bancos.add(banco);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBanco, stmt, conn);

		return bancos;

	}

}