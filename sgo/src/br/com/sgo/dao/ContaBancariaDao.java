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
import br.com.sgo.modelo.ContaBancaria;

@Component
public class ContaBancariaDao extends Dao<ContaBancaria> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContaBancaria;
	
	private final String sqlContaBancaria= "SELECT CONTABANCARIA.contabancaria_id, CONTABANCARIA.numeroconta, CONTABANCARIA.empresa_id" +
			", CONTABANCARIA.organizacao_id, CONTABANCARIA.agencia_id, CONTABANCARIA.tipoconta_id FROM CONTABANCARIA (NOLOCK)";  
	
	public ContaBancariaDao(Session session, ConnJDBC conexao) {
		super(session, ContaBancaria.class);
		this.conexao = conexao;
	}

	public Collection<ContaBancaria> buscaAllContaBancaria(){
		String sql = sqlContaBancaria;
		this.conn = this.conexao.getConexao();
		Collection<ContaBancaria> contas = new ArrayList<ContaBancaria>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsContaBancaria = this.stmt.executeQuery();
			while (rsContaBancaria.next()) {				
				ContaBancaria conta = new ContaBancaria();
				conta.setContaBancaria_id(rsContaBancaria.getLong("contabancaria_id"));
				conta.setNumeroconta(rsContaBancaria.getString("contanumero"));
				contas.add(conta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContaBancaria, stmt, conn);
		return contas;
	}

	public Collection<ContaBancaria> buscaContaBancariaByNumeroConta(String numeroConta){
		String sql = sqlContaBancaria;
		if(numeroConta != null)
			sql += 	" WHERE CONTABANCARIA.numeroconta like ?";
		this.conn = this.conexao.getConexao();
		Collection<ContaBancaria> contas = new ArrayList<ContaBancaria>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  numeroConta + "%");
			this.rsContaBancaria = this.stmt.executeQuery();
			ContaBancaria conta = new ContaBancaria();
			while (rsContaBancaria.next()) {
				conta.setContaBancaria_id(rsContaBancaria.getLong("contabancaria_id"));
				conta.setNumeroconta(rsContaBancaria.getString("numeroconta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContaBancaria, stmt, conn);
		return contas;
	}
	
	public ContaBancaria buscaContaBancariaByAgeNum(Long agencia_id, String numeroconta){
		String sql = sqlContaBancaria;		
		if(agencia_id != null)
			sql += 	" AND CONTABANCARIA.agencia_id = ?";		
		if(numeroconta != null)
			sql += 	" AND (CONTABANCARIA.numeroconta like ?)";
		this.conn = this.conexao.getConexao();		
		ContaBancaria conta = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, agencia_id);			
			this.stmt.setString(2,"%"+  numeroconta + "%");
			this.rsContaBancaria = this.stmt.executeQuery();
			while (rsContaBancaria.next()) {
				conta = new ContaBancaria();
				conta.setContaBancaria_id(rsContaBancaria.getLong("contabancaria_id"));
				conta.setNumeroconta(rsContaBancaria.getString("numeroconta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContaBancaria, stmt, conn);
		return conta;
	}

	public ContaBancaria buscaContaBancariaByEmpOrgAgeNum(Long empresa_id, Long organizacao_id, Long agencia_id, String numeroconta){
		String sql = sqlContaBancaria;		
		if(empresa_id != null)
			sql +=	" WHERE CONTABANCARIA.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND CONTABANCARIA.organizacao_id = ?";
		if(agencia_id != null)
			sql += 	" AND CONTABANCARIA.agencia_id = ?";		
		if(numeroconta != null)
			sql += 	" AND (CONTABANCARIA.numeroconta like ?)";
		this.conn = this.conexao.getConexao();		
		ContaBancaria conta = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, agencia_id);			
			this.stmt.setString(4,"%"+  numeroconta + "%");
			this.rsContaBancaria = this.stmt.executeQuery();
			while (rsContaBancaria.next()) {
				conta = new ContaBancaria();
				conta.setContaBancaria_id(rsContaBancaria.getLong("contabancaria_id"));
				conta.setNumeroconta(rsContaBancaria.getString("numeroconta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContaBancaria, stmt, conn);
		return conta;
	}
	
	public ContaBancaria buscaContaBancariaByEmpOrgAgeTipNum(Long empresa_id, Long organizacao_id, Long agencia_id, Long tipoconta_id, String numeroconta){
		String sql = sqlContaBancaria;
		if(empresa_id != null)
			sql +=	" WHERE CONTABANCARIA.empresa_id = ?";
		if(organizacao_id != null)
			sql += 	" AND CONTABANCARIA.organizacao_id = ?";
		if(agencia_id != null)
			sql += 	" AND CONTABANCARIA.agencia_id = ?";
		if(tipoconta_id != null)
			sql += 	" AND CONTABANCARIA.tipoconta_id = ?";
		if(numeroconta != null)
			sql += 	" AND (CONTABANCARIA.numeroconta like ?)";
		this.conn = this.conexao.getConexao();
		ContaBancaria conta = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, agencia_id);
			this.stmt.setLong(4, tipoconta_id);
			this.stmt.setString(5,"%"+  numeroconta + "%");			
			this.rsContaBancaria = this.stmt.executeQuery();
			while (rsContaBancaria.next()) {
				conta = new ContaBancaria();
				conta.setContaBancaria_id(rsContaBancaria.getLong("contabancaria_id"));
				conta.setNumeroconta(rsContaBancaria.getString("numeroconta"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContaBancaria, stmt, conn);
		return conta;
	}
}