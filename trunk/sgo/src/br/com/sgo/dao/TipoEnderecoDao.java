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
import br.com.sgo.modelo.TipoEndereco;

@Component
public class TipoEnderecoDao extends Dao<TipoEndereco> {


	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoEndereco;
	
	public TipoEnderecoDao(Session session,ConnJDBC conexao) {
		super(session, TipoEndereco.class);
		this.conexao = conexao;
	}

	public Collection<TipoEndereco> buscaTiposEnderecoToLocalidades(){

		/* BUSCA TIPOS DE ENDERECO RESIDENCIAL E ASSINATURA */

		String sql = "SELECT TIPOENDERECO.tipoendereco_id, TIPOENDERECO.nome FROM TIPOENDERECO (NOLOCK) WHERE TIPOENDERECO.tipoendereco_id IN (1,2)  ";

		this.conn = this.conexao.getConexao();

		Collection<TipoEndereco> tiposEndereco = new ArrayList<TipoEndereco>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			
			this.rsTipoEndereco = this.stmt.executeQuery();

			while (rsTipoEndereco.next()) {
				
				TipoEndereco tipoEndereco = new TipoEndereco();
				tipoEndereco.setTipoEndereco_id(rsTipoEndereco.getLong("tipoendereco_id"));
				tipoEndereco.setNome(rsTipoEndereco.getString("nome"));

				tiposEndereco.add(tipoEndereco);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoEndereco, stmt, conn);

		return tiposEndereco;

	}
}