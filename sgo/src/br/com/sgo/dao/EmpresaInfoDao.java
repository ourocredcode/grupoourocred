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
import br.com.sgo.modelo.EmpresaInfo;

@Component
public class EmpresaInfoDao extends Dao<EmpresaInfo> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsEmpresaInfo;
	
	private final String sqlEmpresaInfo= "SELECT EMPRESAINFO.empresa_id, EMPRESAINFO.nome, EMPRESAINFO.empresa_id" +
			", EMPRESAINFO.organizacao_id FROM EMPRESAINFO (NOLOCK)";  
	
	public EmpresaInfoDao(Session session, ConnJDBC conexao) {
		super(session, EmpresaInfo.class);
		this.conexao = conexao;
	}

	public Collection<EmpresaInfo> buscaAllEmpresaInfo(){
		String sql = sqlEmpresaInfo;
		this.conn = this.conexao.getConexao();
		Collection<EmpresaInfo> empresas = new ArrayList<EmpresaInfo>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsEmpresaInfo = this.stmt.executeQuery();
			while (rsEmpresaInfo.next()) {				
				EmpresaInfo empresa = new EmpresaInfo();
				empresa.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				empresa.setNome(rsEmpresaInfo.getString("nome"));
				empresas.add(empresa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return empresas;
	}

	public Collection<EmpresaInfo> buscaEmpresaInfoByNome(String nome){
		String sql = sqlEmpresaInfo;
		if(nome != null)
			sql += 	" WHERE EMPRESAINFO.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<EmpresaInfo> empresas = new ArrayList<EmpresaInfo>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsEmpresaInfo = this.stmt.executeQuery();
			EmpresaInfo empresa = new EmpresaInfo();
			while (rsEmpresaInfo.next()) {
				empresa.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				empresa.setNome(rsEmpresaInfo.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return empresas;
	}
	
	public EmpresaInfo buscaEmpresaInfoByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlEmpresaInfo;
		if(empresa != null)
			sql += 	" AND EMPRESAINFO.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND EMPRESAINFO.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (EMPRESAINFO.nome like ?)";
		this.conn = this.conexao.getConexao();		
		EmpresaInfo emp = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsEmpresaInfo = this.stmt.executeQuery();
			while (rsEmpresaInfo.next()) {
				emp = new EmpresaInfo();
				emp.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				emp.setNome(rsEmpresaInfo.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return emp;
	}

}