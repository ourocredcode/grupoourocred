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
import br.com.sgo.modelo.ParceiroBeneficio;

@Component
public class ParceiroBeneficioDao extends Dao<ParceiroBeneficio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroBeneficio;
	
	private final String sqlParceiroBeneficio= "SELECT PARCEIROBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.nome, PARCEIROBENEFICIO.empresa_id" +
			", PARCEIROBENEFICIO.organizacao_id FROM PARCEIROBENEFICIO (NOLOCK)";  
	
	public ParceiroBeneficioDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroBeneficio.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroBeneficio> buscaAllParceiroBeneficio(){
		String sql = sqlParceiroBeneficio;
		this.conn = this.conexao.getConexao();
		Collection<ParceiroBeneficio> beneficios = new ArrayList<ParceiroBeneficio>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsParceiroBeneficio = this.stmt.executeQuery();
			while (rsParceiroBeneficio.next()) {				
				ParceiroBeneficio beneficio = new ParceiroBeneficio();
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio.getLong("parceirobeneficio_id"));
				beneficio.setNome(rsParceiroBeneficio.getString("nome"));
				beneficios.add(beneficio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return beneficios;
	}

	public Collection<ParceiroBeneficio> buscaParceiroBeneficioByNome(String nome){
		String sql = sqlParceiroBeneficio;
		if(nome != null)
			sql += 	" WHERE PARCEIROBENEFICIO.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<ParceiroBeneficio> beneficios = new ArrayList<ParceiroBeneficio>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsParceiroBeneficio = this.stmt.executeQuery();
			ParceiroBeneficio beneficio = new ParceiroBeneficio();
			while (rsParceiroBeneficio.next()) {
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio.getLong("parceirobeneficio_id"));
				beneficio.setNome(rsParceiroBeneficio.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return beneficios;
	}
	
	public ParceiroBeneficio buscaParceiroBeneficioByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlParceiroBeneficio;
		if(empresa != null)
			sql += 	" AND PARCEIROBENEFICIO.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND PARCEIROBENEFICIO.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (PARCEIROBENEFICIO.nome like ?)";
		this.conn = this.conexao.getConexao();		
		ParceiroBeneficio beneficio = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsParceiroBeneficio = this.stmt.executeQuery();
			while (rsParceiroBeneficio.next()) {
				beneficio = new ParceiroBeneficio();
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio.getLong("parceirobeneficio_id"));
				beneficio.setNome(rsParceiroBeneficio.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return beneficio;
	}

}