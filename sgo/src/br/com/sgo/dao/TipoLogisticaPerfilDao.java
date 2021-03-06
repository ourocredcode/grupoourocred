package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.TipoLogisticaPerfil;

@Component
public class TipoLogisticaPerfilDao extends Dao<TipoLogisticaPerfil> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoLogisticaPerfil;

	private Calendar dataAtual = Calendar.getInstance();

	private final String sqlTipoLogisticaPerfil = "SELECT TIPOLOGISTICAPERFIL.tipologistica_id, TIPOLOGISTICA.nome AS tipologistica_nome, " +
							" TIPOLOGISTICAPERFIL.perfil_id, PERFIL.nome AS perfil_nome, "+
							" TIPOLOGISTICAPERFIL.empresa_id, EMPRESA.nome AS empresa_nome "+
							", TIPOLOGISTICAPERFIL.organizacao_id, ORGANIZACAO.nome AS organizacao_nome, TIPOLOGISTICAPERFIL.isactive "+
							" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
							" INNER JOIN TIPOLOGISTICAPERFIL (NOLOCK) ON EMPRESA.empresa_id = TIPOLOGISTICAPERFIL.empresa_id) ON ORGANIZACAO.organizacao_id = TIPOLOGISTICAPERFIL.organizacao_id) "+ 
							" INNER JOIN PERFIL (NOLOCK) ON TIPOLOGISTICAPERFIL.perfil_id = PERFIL.perfil_id) "+
							" INNER JOIN TIPOLOGISTICA (NOLOCK) ON TIPOLOGISTICAPERFIL.tipologistica_id = TIPOLOGISTICA.tipologistica_id ";

	public TipoLogisticaPerfilDao(Session session, ConnJDBC conexao) {

		super(session, TipoLogisticaPerfil.class);
		this.conexao = conexao;

	}

	public Collection<TipoLogisticaPerfil> buscaTodosTipoLogisticaPerfil() {
		
		String sql = sqlTipoLogisticaPerfil;

		this.conn = this.conexao.getConexao();
		
		Collection<TipoLogisticaPerfil> tiposLogisticaPerfil = new ArrayList<TipoLogisticaPerfil>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.rsTipoLogisticaPerfil = this.stmt.executeQuery();
			
			while (rsTipoLogisticaPerfil.next()) {

				getTipoLogisticaPerfil(tiposLogisticaPerfil);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		this.conexao.closeConnection(rsTipoLogisticaPerfil, stmt, conn);
		
		return tiposLogisticaPerfil;
	}

	public Collection<TipoLogisticaPerfil> buscaTipoLogisticaPerfilPorEmpresaOrganizacaoPerfil(Long empresa_id, Long organizacao_id, Long tipoLogistica_id) {
		
		String sql = sqlTipoLogisticaPerfil;

		if (empresa_id != null)
			sql += " WHERE TIPOLOGISTICAPERFIL.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOLOGISTICAPERFIL.organizacao_id = ?";
		if (tipoLogistica_id != null)
			sql += " AND TIPOLOGISTICAPERFIL.tipologistica_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoLogisticaPerfil> tiposLogisticaPerfil = new ArrayList<TipoLogisticaPerfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoLogistica_id);

			this.rsTipoLogisticaPerfil = this.stmt.executeQuery();

			while (rsTipoLogisticaPerfil.next()) {

				getTipoLogisticaPerfil(tiposLogisticaPerfil);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoLogisticaPerfil, stmt, conn);
		return tiposLogisticaPerfil;
	}

	public TipoLogisticaPerfil buscaTipoLogisticaPerfilPorEmpresaOrganizacaoWorkflowPerfil(Long empresa_id, Long organizacao_id, Long tipoLogistica_id, Long perfil_id) {
		
		String sql = sqlTipoLogisticaPerfil;

		if (empresa_id != null)
			sql += " WHERE TIPOLOGISTICAPERFIL.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOLOGISTICAPERFIL.organizacao_id = ?";
		if (tipoLogistica_id != null)
			sql += " AND TIPOLOGISTICAPERFIL.tipologistica_id = ?";
		if (perfil_id != null)
			sql += " AND TIPOLOGISTICAPERFIL.perfil_id = ?";

		this.conn = this.conexao.getConexao();

		TipoLogisticaPerfil tipoLogisticaPerfil = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoLogistica_id);
			this.stmt.setLong(4, perfil_id);

			this.rsTipoLogisticaPerfil = this.stmt.executeQuery();

			while (rsTipoLogisticaPerfil.next()) {

				tipoLogisticaPerfil = new  TipoLogisticaPerfil();

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsTipoLogisticaPerfil.getLong("perfil_id"));

				tipoLogisticaPerfil.setPerfil(perfil);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTipoLogisticaPerfil, stmt, conn);
		
		return tipoLogisticaPerfil;
	}


	public void insert(TipoLogisticaPerfil tipoLogisticaPerfil) throws SQLException {

		String sql = "INSERT INTO TIPOLOGISTICAPERFIL (empresa_id, organizacao_id, tipologistica_id, perfil_id, createdby, updatedby, created, updated, isleituraescrita, isactive) " +
													 " VALUES (?,?,?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, tipoLogisticaPerfil.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, tipoLogisticaPerfil.getOrganizacao().getOrganizacao_id());			
			this.stmt.setLong(3, tipoLogisticaPerfil.getTipoLogistica().getTipoLogistica_id());
			this.stmt.setLong(4, tipoLogisticaPerfil.getPerfil().getPerfil_id());
			this.stmt.setLong(5, tipoLogisticaPerfil.getCreatedBy().getUsuario_id());
			this.stmt.setLong(6, tipoLogisticaPerfil.getUpdatedBy().getUsuario_id());			
			this.stmt.setTimestamp(7, new Timestamp(dataAtual.getTimeInMillis()));
			this.stmt.setTimestamp(8, new Timestamp(dataAtual.getTimeInMillis()));
			this.stmt.setBoolean(9, tipoLogisticaPerfil.getIsLeituraEscrita());
			this.stmt.setBoolean(10, tipoLogisticaPerfil.getIsActive());

			this.stmt.executeUpdate();
			this.conn.commit();
			
		} catch (SQLException e) {

			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}

		this.conexao.closeConnection(stmt, conn);

	}

	private void getTipoLogisticaPerfil(Collection<TipoLogisticaPerfil> tiposLogisticaPerfil) throws SQLException {

		TipoLogisticaPerfil tipoLogisticaPerfil = new TipoLogisticaPerfil();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		TipoLogistica tipoLogistica = new TipoLogistica();
		Perfil perfil = new Perfil();

		empresa.setEmpresa_id(rsTipoLogisticaPerfil.getLong("empresa_id"));
		empresa.setNome(rsTipoLogisticaPerfil.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsTipoLogisticaPerfil.getLong("organizacao_id"));
		organizacao.setNome(rsTipoLogisticaPerfil.getString("organizacao_nome"));

		tipoLogistica.setTipoLogistica_id(rsTipoLogisticaPerfil.getLong("tipologistica_id"));
		tipoLogistica.setNome(rsTipoLogisticaPerfil.getString("tipologistica_nome"));

		perfil.setPerfil_id(rsTipoLogisticaPerfil.getLong("perfil_id"));
		perfil.setNome(rsTipoLogisticaPerfil.getString("perfil_nome"));

		tipoLogisticaPerfil.setEmpresa(empresa);
		tipoLogisticaPerfil.setOrganizacao(organizacao);
		tipoLogisticaPerfil.setTipoLogistica(tipoLogistica);
		tipoLogisticaPerfil.setPerfil(perfil);

		tiposLogisticaPerfil.add(tipoLogisticaPerfil);

	}

}
