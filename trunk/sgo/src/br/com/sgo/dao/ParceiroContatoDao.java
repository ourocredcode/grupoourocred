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
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.TipoContato;

@Component
public class ParceiroContatoDao extends Dao<ParceiroContato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroContato;
	
	private String sqlParceiroContato = " SELECT PARCEIROCONTATO.parceirocontato_id, PARCEIROCONTATO.tipocontato_id, TIPOCONTATO.tipocontato_id, " +
			"		TIPOCONTATO.nome as tipocontato_nome , TIPOCONTATO.chave as tipocontato_chave , PARCEIROCONTATO.nome as parceirocontato_nome " +
			"			FROM " +
			"				(PARCEIROCONTATO (NOLOCK) INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROCONTATO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +
			"				INNER JOIN TIPOCONTATO (NOLOCK) ON PARCEIROCONTATO.tipocontato_id = TIPOCONTATO.tipocontato_id " +
			"			WHERE PARCEIROCONTATO.isactive = 1 ";

	public ParceiroContatoDao(Session session,ConnJDBC conexao) {
		super(session, ParceiroContato.class);
		this.conexao = conexao;
	}
	
	public Collection<ParceiroContato> buscaParceiroContatos(Long parceiroNegocio_id){

		if(parceiroNegocio_id != null)
			sqlParceiroContato += " AND PARCEIRONEGOCIO.parceironegocio_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroContato> parceiroContatos = new ArrayList<ParceiroContato>();

		try {

			this.stmt = conn.prepareStatement(sqlParceiroContato);			
			this.stmt.setLong(1,parceiroNegocio_id);			

			this.rsParceiroContato = this.stmt.executeQuery();

			while (rsParceiroContato.next()) {

				ParceiroContato parceiroContato = new ParceiroContato();

				parceiroContato.setParceiroContato_id(rsParceiroContato.getLong("parceirocontato_id"));
				parceiroContato.setNome(rsParceiroContato.getString("parceirocontato_nome"));

				TipoContato tipoContato = new TipoContato();

				tipoContato.setTipoContato_id(rsParceiroContato.getLong("tipocontato_id"));
				tipoContato.setNome(rsParceiroContato.getString("tipocontato_nome"));
				tipoContato.setChave(rsParceiroContato.getString("tipocontato_chave"));

				parceiroContato.setTipoContato(tipoContato);

				parceiroContatos.add(parceiroContato);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroContato, stmt, conn);
		return parceiroContatos;

	}
	
	public ParceiroContato buscaParceiroContato(Long tipoContato_id, String nome){

		String sql = "SELECT PARCEIROCONTATO.parceirocontato_id " +
				"	FROM PARCEIROCONTATO WHERE PARCEIROCONTATO.tipocontato_id = ? AND nome like ? AND isactive = 1 ";

		this.conn = this.conexao.getConexao();

		ParceiroContato parceiroContato = null;

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1,tipoContato_id);
			this.stmt.setString(2,'%' + nome + '%');

			this.rsParceiroContato = this.stmt.executeQuery();

			while (rsParceiroContato.next()) {
				
				parceiroContato = new ParceiroContato();

				parceiroContato.setParceiroContato_id(rsParceiroContato.getLong("parceirocontato_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroContato, stmt, conn);
		return parceiroContato;

	}

}