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
import br.com.sgo.modelo.ParceiroInfoBanco;

@Component
public class ParceiroInfoBancoDao extends Dao<ParceiroInfoBanco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroInfoBanco;
	
	private final String sqlParceiroInfoBancos = "SELECT PARCEIROINFOBANCO.parceiroinfobanco_id, PARCEIROINFOBANCO.nome, PARCEIROINFOBANCO.empresa_id, PARCEIROINFOBANCO.organizacao_id," +
			"PARCEIROINFOBANCO.parceironegocio_id, PARCEIROINFOBANCO.banco_id, PARCEIROINFOBANCO.agencia_id, PARCEIROINFOBANCO.contabancaria_id, PARCEIROINFOBANCO.meiopagamento_id, PARCEIROINFOBANCO.isactive FROM PARCEIROINFOBANCO (NOLOCK)";  
	
	public ParceiroInfoBancoDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroInfoBanco.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroInfoBanco> buscaAllParceiroInfoBanco(){
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

	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPa(Long empresa_id, Long organizacao_id, Long parceironegocio_id){
		String sql = sqlParceiroInfoBancos;		
		if(empresa_id != null)
			sql +=	" WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND PARCEIROINFOBANCO.organizacao_id = ?";
		if(parceironegocio_id != null)
			sql += 	" AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		this.conn = this.conexao.getConexao();		
		ParceiroInfoBanco informacaoParceiro = null;		
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceironegocio_id);
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
	
	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBa(Long empresa_id, Long organizacao_id, Long parceironegocio_id, Long banco_id){
		String sql = sqlParceiroInfoBancos;		
		if(empresa_id != null)
			sql +=	" WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND PARCEIROINFOBANCO.organizacao_id = ?";
		if(parceironegocio_id != null)
			sql += 	" AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if(banco_id != null)
			sql += 	" AND PARCEIROINFOBANCO.banco_id = ?";
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
	
	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBaAg(Long empresa_id, Long organizacao_id, Long parceironegocio_id, Long banco_id, Long agencia_id){
		String sql = sqlParceiroInfoBancos;		
		if(empresa_id != null)
			sql +=	" WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND PARCEIROINFOBANCO.organizacao_id = ?";
		if(parceironegocio_id != null)
			sql += 	" AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if(banco_id != null)
			sql += 	" AND PARCEIROINFOBANCO.banco_id = ?";
		if(agencia_id != null)
			sql += 	" AND PARCEIROINFOBANCO.agencia_id = ?";
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
	
	public ParceiroInfoBanco buscaParceiroInfoBancoByEmOrPaBaAgCb(Long empresa_id, Long organizacao_id, Long parceironegocio_id, Long banco_id, Long agencia_id, 
			Long contabancaria_id){
		String sql = sqlParceiroInfoBancos;		
		if(empresa_id != null)
			sql +=	" WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND PARCEIROINFOBANCO.organizacao_id = ?";
		if(parceironegocio_id != null)
			sql += 	" AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if(banco_id != null)
			sql += 	" AND PARCEIROINFOBANCO.banco_id = ?";
		if(agencia_id != null)
			sql += 	" AND PARCEIROINFOBANCO.agencia_id = ?";
		if(contabancaria_id != null)
			sql += 	" AND PARCEIROINFOBANCO.contabancaria_id = ?";		
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
	
	public ParceiroInfoBanco buscaParceiroInfoBancoByAllTypes(Long empresa_id, Long organizacao_id, Long parceironegocio_id, Long banco_id, Long agencia_id, 
			Long contabancaria_id, String nome){
		String sql = sqlParceiroInfoBancos;		
		if(empresa_id != null)
			sql +=	" WHERE PARCEIROINFOBANCO.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND PARCEIROINFOBANCO.organizacao_id = ?";
		if(parceironegocio_id != null)
			sql += 	" AND PARCEIROINFOBANCO.parceironegocio_id = ?";
		if(banco_id != null)
			sql += 	" AND PARCEIROINFOBANCO.banco_id = ?";
		if(agencia_id != null)
			sql += 	" AND PARCEIROINFOBANCO.agencia_id = ?";
		if(contabancaria_id != null)
			sql += 	" AND PARCEIROINFOBANCO.contabancaria_id = ?";
		if(nome != null)
			sql += 	" AND (PARCEIROINFOBANCO.nome like ?)";
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
			this.stmt.setString(7,"%"+  nome + "%");		
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