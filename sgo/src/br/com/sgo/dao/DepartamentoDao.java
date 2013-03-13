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
import br.com.sgo.modelo.Departamento;

@Component
public class DepartamentoDao extends Dao<Departamento> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsDepartamentos;

	public DepartamentoDao(Session session , ConnJDBC conexao) {
		super(session, Departamento.class);
		this.session = session;
		this.conexao =conexao;
	}
	
	public Collection<Departamento> buscaDepartamentos(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select DEPARTAMENTO.departamento_id, DEPARTAMENTO.nome from DEPARTAMENTO (NOLOCK) WHERE DEPARTAMENTO.empresa_id = ? AND DEPARTAMENTO.organizacao_id = ? AND DEPARTAMENTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Departamento> departamentos = new ArrayList<Departamento>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsDepartamentos = this.stmt.executeQuery();

			while (rsDepartamentos.next()) {
				Departamento departamento = new Departamento();

				departamento.setDepartamento_id(rsDepartamentos.getLong("departamento_id"));				
				departamento.setNome(rsDepartamentos.getString("nome"));

				departamentos.add(departamento);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsDepartamentos, stmt, conn);

		return departamentos;

	}
	
	public Collection<Departamento> buscaDepartamentos(Long empresa_id, Long organizacao_id){

		String sql = "select DEPARTAMENTO.departamento_id, DEPARTAMENTO.nome from DEPARTAMENTO (NOLOCK) WHERE DEPARTAMENTO.empresa_id = ? AND DEPARTAMENTO.organizacao_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Departamento> detapartamentos = new ArrayList<Departamento>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);

			this.rsDepartamentos = this.stmt.executeQuery();

			while (rsDepartamentos.next()) {
				Departamento departamento = new Departamento();

				departamento.setDepartamento_id(rsDepartamentos.getLong("departamento_id"));
				departamento.setNome(rsDepartamentos.getString("nome"));

				detapartamentos.add(departamento);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsDepartamentos, stmt, conn);

		return detapartamentos;

	}

}
