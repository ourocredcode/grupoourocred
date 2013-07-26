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
import br.com.sgo.modelo.EmpresaInfo;

@Component
public class EmpresaInfoDao extends Dao<EmpresaInfo> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsEmpresaInfo;

	private final String sqlEmpresaInfo = "SELECT EMPRESAINFO.empresa_id, EMPRESAINFO.nome FROM EMPRESAINFO (NOLOCK)";

	public EmpresaInfoDao(Session session, ConnJDBC conexao) {

		super(session, EmpresaInfo.class);
		this.conexao = conexao;

	}

	public Collection<EmpresaInfo> buscaAllEmpresaInfo() {

		String sql = sqlEmpresaInfo;

		this.conn = this.conexao.getConexao();

		Collection<EmpresaInfo> infosEmpresa = new ArrayList<EmpresaInfo>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsEmpresaInfo = this.stmt.executeQuery();

			while (rsEmpresaInfo.next()) {
			
				EmpresaInfo infoEmpresa = new EmpresaInfo();

				infoEmpresa.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				infoEmpresa.setNome(rsEmpresaInfo.getString("nome"));

				infosEmpresa.add(infoEmpresa);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return infosEmpresa;
	}

	public EmpresaInfo buscaEmpresaById(Long empresa_id) {

		String sql = sqlEmpresaInfo;

		if (empresa_id != null)
			sql += " WHERE EMPRESAINFO.empresa_id = ?";

		this.conn = this.conexao.getConexao();

		EmpresaInfo empresaInfo = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);

			this.rsEmpresaInfo = this.stmt.executeQuery();			

			while (rsEmpresaInfo.next()) {

				empresaInfo = new EmpresaInfo();
				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				empresaInfo.setEmpresa_id(empresa_id);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return empresaInfo;
	}

	public EmpresaInfo buscaEmpresaInfoByEmOrNo(Long empresa, String nome) {

		String sql = sqlEmpresaInfo;

		if (empresa != null)
			sql += " AND EMPRESAINFO.empresa_id = ?";
		if (nome != null)
			sql += " AND (EMPRESAINFO.nome like ?)";

		this.conn = this.conexao.getConexao();

		EmpresaInfo infoEmpresa = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa);
			this.stmt.setString(2, "%" + nome + "%");

			this.rsEmpresaInfo = this.stmt.executeQuery();

			while (rsEmpresaInfo.next()) {

				infoEmpresa = new EmpresaInfo();

				infoEmpresa.setEmpresa_id(rsEmpresaInfo.getLong("empresa_id"));
				infoEmpresa.setNome(rsEmpresaInfo.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEmpresaInfo, stmt, conn);
		return infoEmpresa;
	}
}