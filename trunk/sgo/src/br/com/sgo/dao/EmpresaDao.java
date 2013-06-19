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
import br.com.sgo.modelo.Empresa;

@Component
public class EmpresaDao extends Dao<Empresa> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsEmpresas;

	public EmpresaDao(Session session, ConnJDBC conexao) {

		super(session, Empresa.class);
		this.conexao = conexao;

	}

	public Collection<Empresa> buscaEmpresas(String nome) {

		String sql = "select empresa_id, nome from EMPRESA (NOLOCK) WHERE nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<Empresa> empresas = new ArrayList<Empresa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");
			this.rsEmpresas = this.stmt.executeQuery();

			while (rsEmpresas.next()) {

				Empresa e = new Empresa();

				e.setEmpresa_id(rsEmpresas.getLong("empresa_id"));
				e.setNome(rsEmpresas.getString("nome"));

				empresas.add(e);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEmpresas, stmt, conn);

		return empresas;

	}

}
