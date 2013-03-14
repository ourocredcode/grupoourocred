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
import br.com.sgo.modelo.ParceiroNegocio;

@Component
public class ParceiroNegocioDao extends Dao<ParceiroNegocio> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroNegocio;

	public ParceiroNegocioDao(Session session , ConnJDBC conexao) {
		super(session, ParceiroNegocio.class);
		this.session = session;
		this.conexao =conexao;
	}

	/*
	public Long salva(ParceiroNegocio p){
		this.session.saveOrUpdate(p);
		return p.getParceiroNegocio_id();
	}
	*/

	public Collection<ParceiroNegocio> buscaParceiroNegocio(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.nome from PARCEIRONEGOCIO (NOLOCK) " +
				"	WHERE PARCEIRONEGOCIO.isactive=1 AND PARCEIRONEGOCIO.empresa_id = ? AND PARCEIRONEGOCIO.organizacao_id = ? AND PARCEIRONEGOCIO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroNegocio> parceiros = new ArrayList<ParceiroNegocio>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			

			this.rsParceiroNegocio = this.stmt.executeQuery();

			while (rsParceiroNegocio.next()) {
				ParceiroNegocio parceiro = new ParceiroNegocio();

				parceiro.setParceiroNegocio_id(rsParceiroNegocio.getLong("parceironegocio_id"));				
				parceiro.setNome(rsParceiroNegocio.getString("nome"));

				parceiros.add(parceiro);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroNegocio, stmt, conn);
		return parceiros;
	}

}
