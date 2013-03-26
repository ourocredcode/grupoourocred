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
import br.com.sgo.modelo.ElementoBd;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class ElementoBdDao extends Dao<ElementoBd> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsElementos;	
	private final String sqlElementosBd = "SELECT EMPRESA.nome as empresa_nome, EMPRESA.empresa_id, ORGANIZACAO.nome as organizacao_nome, ORGANIZACAO.organizacao_id, " +
											"ELEMENTOBD.nomecolunabd, ELEMENTOBD.elementobd_id FROM (EMPRESA (NOLOCK) INNER JOIN ELEMENTOBD (NOLOCK) ON EMPRESA.empresa_id = ELEMENTOBD.empresa_id) " +
											"INNER JOIN ORGANIZACAO (NOLOCK) ON ELEMENTOBD.organizacao_id = ORGANIZACAO.organizacao_id"; 

	public ElementoBdDao(Session session,ConnJDBC conexao) {
		super(session, ElementoBd.class);	
		this.conexao = conexao;
	}

	public Collection<ElementoBd> buscaElementos(){
		String sql = sqlElementosBd;		
		this.conn = this.conexao.getConexao();
		Collection<ElementoBd> elementosbd = new ArrayList<ElementoBd>();
		try {	
			this.stmt = conn.prepareStatement(sql);			
			this.rsElementos = this.stmt.executeQuery();
			while (rsElementos.next()) {
				ElementoBd elementobd = new ElementoBd();				
				elementobd.setElementoBd_id(rsElementos.getLong("elementobd_id"));				
				elementobd.setNomeColunaBd(rsElementos.getString("nomecolunabd"));
				elementosbd.add(elementobd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		this.conexao.closeConnection(rsElementos, stmt, conn);
		return elementosbd;
	}

	public Collection<ElementoBd> buscaElementosLista(Long empresa_id){

		String sql = sqlElementosBd;

		if(empresa_id != null)
			sql +=	" WHERE EMPRESA.empresa_id = ? ";			

		this.conn = this.conexao.getConexao();
		Collection<ElementoBd> elementosBd = new ArrayList<ElementoBd>();
		try {
			this.stmt = conn.prepareStatement(sql);
			if(empresa_id != null)
				this.stmt.setLong(1, empresa_id);
			this.rsElementos = this.stmt.executeQuery();
			while (rsElementos.next()) {
				ElementoBd elementoBd = new ElementoBd();
				Empresa e = new Empresa();
				e.setEmpresa_id(rsElementos.getLong("empresa_id"));
				e.setNome(rsElementos.getString("empresa_nome"));
				elementoBd.setEmpresa(e);
				elementosBd.add(elementoBd);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsElementos, stmt, conn);
		return elementosBd;
	}
	
	public Collection<ElementoBd> buscaElementosLista(Long empresa_id, Long organizacao_id){
		String sql = sqlElementosBd;
		if(empresa_id != null)
			sql +=	" WHERE EMPRESA.empresa_id = ? ";
		if(organizacao_id != null)
			sql += 	" AND ORGANIZACAO.organizacao_id = ?  ";
			
		this.conn = this.conexao.getConexao();
		Collection<ElementoBd> elementosBd = new ArrayList<ElementoBd>();
		try {			
			this.stmt = conn.prepareStatement(sql);
			if(empresa_id != null)
				this.stmt.setLong(1,empresa_id);
			if(organizacao_id != null)
				this.stmt.setLong(2, organizacao_id);			
			this.rsElementos = this.stmt.executeQuery();
			while (rsElementos.next()) {
				ElementoBd elementoBd = new ElementoBd();
				Empresa e = new Empresa();
				Organizacao o = new Organizacao();
				e.setEmpresa_id(rsElementos.getLong("empresa_id"));
				e.setNome(rsElementos.getString("empresa_nome"));
				o.setOrganizacao_id(rsElementos.getLong("organizacao_id"));
				o.setNome(rsElementos.getString("organizacao_nome"));				
				elementoBd.setEmpresa(e);
				elementoBd.setOrganizacao(o);
				elementosBd.add(elementoBd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsElementos, stmt, conn);
		return elementosBd;
	}
	
	public Collection<ElementoBd> buscaElementosLista(Long empresa_id, Long organizacao_id, String nomeColunaBd){
		String sql = sqlElementosBd;		
			if(empresa_id != null)
				sql +=	" WHERE EMPRESA.empresa_id = ? ";
			if(organizacao_id != null)
				sql += 	" AND ORGANIZACAO.organizacao_id = ?  ";
			if(!nomeColunaBd.equals(""))
				sql +=	" AND ELEMENTOBD.nomecolunabd like ?";
		this.conn = this.conexao.getConexao();
		Collection<ElementoBd> elementosBd = new ArrayList<ElementoBd>();
		try {			
			this.stmt = conn.prepareStatement(sql);

			if(empresa_id != null)
				this.stmt.setLong(1,empresa_id);
			if(organizacao_id != null)
				this.stmt.setLong(2, organizacao_id);
			if(!nomeColunaBd.equals(""))
				this.stmt.setString(3,"%" + nomeColunaBd + "%");

			this.rsElementos = this.stmt.executeQuery();
			while (rsElementos.next()) {
				ElementoBd elementoBd = new ElementoBd();
				Empresa e = new Empresa();
				Organizacao o = new Organizacao();
				e.setEmpresa_id(rsElementos.getLong("empresa_id"));
				e.setNome(rsElementos.getString("empresa_nome"));
				o.setOrganizacao_id(rsElementos.getLong("organizacao_id"));
				o.setNome(rsElementos.getString("organizacao_nome"));
				elementoBd.setElementoBd_id(rsElementos.getLong("elementobd_id"));				
				elementoBd.setNomeColunaBd(rsElementos.getString("nomecolunabd"));
				elementoBd.setEmpresa(e);
				elementoBd.setOrganizacao(o);
				elementosBd.add(elementoBd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsElementos, stmt, conn);
		return elementosBd;
	}
}
