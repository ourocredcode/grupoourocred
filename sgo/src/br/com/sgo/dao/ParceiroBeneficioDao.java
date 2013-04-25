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
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroNegocio;

@Component
public class ParceiroBeneficioDao extends Dao<ParceiroBeneficio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsParceiroBeneficio;

	private final String sqlParceiroBeneficio = " SELECT "
			+ "	PARCEIROBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.empresa_id, EMPRESA.nome,  "
			+ "	PARCEIROBENEFICIO.organizacao_id, ORGANIZACAO.nome, PARCEIROBENEFICIO.parceironegocio_id, "
			+ "	PARCEIROBENEFICIO.numerobeneficio FROM (( PARCEIROBENEFICIO (NOLOCK) "
			+ "		INNER JOIN EMPRESA (NOLOCK) ON PARCEIROBENEFICIO.empresa_id = EMPRESA.empresa_id) "
			+ "		INNER JOIN ORGANIZACAO (NOLOCK) ON PARCEIROBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "
			+ "		INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";
	
	private final String sqlParceiroBeneficioToHiscon = "SELECT PARCEIROBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.numerobeneficio "+
	", PARCEIROBENEFICIO.empresa_id, EMPRESA.nome AS empresa_nome "+
	", PARCEIROBENEFICIO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
	", PARCEIROBENEFICIO.parceironegocio_id, PARCEIRONEGOCIO.nome AS parceironegocio_nome, PARCEIRONEGOCIO.cpf "+
	" FROM ((PARCEIROBENEFICIO (NOLOCK) INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) "+ 
	" INNER JOIN ORGANIZACAO (NOLOCK) ON PARCEIROBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "+
	" INNER JOIN EMPRESA (NOLOCK) ON PARCEIROBENEFICIO.empresa_id = EMPRESA.empresa_id ";
	
	public ParceiroBeneficioDao(Session session, ConnJDBC conexao) {
		super(session, ParceiroBeneficio.class);
		this.conexao = conexao;
	}

	public Collection<ParceiroBeneficio> buscaAllParceiroBeneficio() {

		String sql = sqlParceiroBeneficio;
		this.conn = this.conexao.getConexao();
		Collection<ParceiroBeneficio> beneficios = new ArrayList<ParceiroBeneficio>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsParceiroBeneficio = this.stmt.executeQuery();
			while (rsParceiroBeneficio.next()) {
				ParceiroBeneficio beneficio = new ParceiroBeneficio();
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio
						.getLong("parceirobeneficio_id"));
				beneficio.setNumeroBeneficio(rsParceiroBeneficio
						.getString("numeroBeneficio"));
				beneficios.add(beneficio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return beneficios;

	}
	
	public ParceiroBeneficio buscaParceiroBeneficioByNumeroBeneficio(String numeroBeneficio) {

		String sql = sqlParceiroBeneficio;
		ParceiroBeneficio parceiroBeneficio = new ParceiroBeneficio();

		if (numeroBeneficio != null)
			sql += " WHERE PARCEIROBENEFICIO.numerobeneficio = ?";

		this.conn = this.conexao.getConexao();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1,numeroBeneficio);
			this.rsParceiroBeneficio = this.stmt.executeQuery();

			while (rsParceiroBeneficio.next()) {

				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

				parceiroNegocio.setParceiroNegocio_id(rsParceiroBeneficio.getLong("parceironegocio_id"));

				parceiroBeneficio.setParceiroBeneficio_id(rsParceiroBeneficio
						.getLong("parceirobeneficio_id"));
				parceiroBeneficio.setNumeroBeneficio(rsParceiroBeneficio
						.getString("numeroBeneficio"));
				
				parceiroBeneficio.setParceiroNegocio(parceiroNegocio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		
		return parceiroBeneficio;

	}

	public Collection<ParceiroBeneficio> buscaParceiroBeneficioByParceiroNegocio(
			Long parceironegocio_id) {

		String sql = sqlParceiroBeneficio;

		if (parceironegocio_id != null)
			sql += " WHERE PARCEIROBENEFICIO.parceironegocio_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ParceiroBeneficio> beneficios = new ArrayList<ParceiroBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceironegocio_id);
			this.rsParceiroBeneficio = this.stmt.executeQuery();

			while (rsParceiroBeneficio.next()) {

				ParceiroBeneficio beneficio = new ParceiroBeneficio();

				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio
						.getLong("parceirobeneficio_id"));
				beneficio.setNumeroBeneficio(rsParceiroBeneficio
						.getString("numeroBeneficio"));

				beneficios.add(beneficio);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);

		return beneficios;

	}

	public Collection<ParceiroBeneficio> buscaParceiroBeneficioByNome(
			String nome) {
		String sql = sqlParceiroBeneficio;
		if (nome != null)
			sql += " WHERE PARCEIROBENEFICIO.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<ParceiroBeneficio> beneficios = new ArrayList<ParceiroBeneficio>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsParceiroBeneficio = this.stmt.executeQuery();
			ParceiroBeneficio beneficio = new ParceiroBeneficio();
			while (rsParceiroBeneficio.next()) {
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio
						.getLong("parceirobeneficio_id"));
				beneficio.setNumeroBeneficio(rsParceiroBeneficio
						.getString("numeroBeneficio"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return beneficios;
	}

	public ParceiroBeneficio buscaParceiroBeneficioPorNumeroBeneficio(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String sql = sqlParceiroBeneficioToHiscon;

		if (numeroBeneficio != null)
			sql += " WHERE PARCEIROBENEFICIO.empresa_id = ?";		
		if (numeroBeneficio != null)
			sql += " AND PARCEIROBENEFICIO.organizacao_id = ?";
		if (numeroBeneficio != null)
			sql += " AND PARCEIROBENEFICIO.numerobeneficio = ?";

		this.conn = this.conexao.getConexao();

		ParceiroBeneficio parceiroBeneficio = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, numeroBeneficio);
			
			this.rsParceiroBeneficio = this.stmt.executeQuery();

			while (rsParceiroBeneficio.next()) {
				
				parceiroBeneficio = new ParceiroBeneficio();
				
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

				empresa.setEmpresa_id(rsParceiroBeneficio.getLong("empresa_id"));
				empresa.setNome(rsParceiroBeneficio.getString("empresa_nome"));

				organizacao.setOrganizacao_id(rsParceiroBeneficio.getLong("organizacao_id"));
				organizacao.setNome(rsParceiroBeneficio.getString("organizacao_nome"));

				parceiroNegocio.setParceiroNegocio_id(rsParceiroBeneficio.getLong("parceironegocio_id"));
				parceiroNegocio.setNome(rsParceiroBeneficio.getString("parceironegocio_nome"));
				parceiroNegocio.setCpf(rsParceiroBeneficio.getString("cpf"));

				parceiroBeneficio.setEmpresa(empresa);
				parceiroBeneficio.setOrganizacao(organizacao);
				parceiroBeneficio.setParceiroNegocio(parceiroNegocio);
				parceiroBeneficio.setParceiroBeneficio_id(rsParceiroBeneficio.getLong("parceirobeneficio_id"));
				parceiroBeneficio.setNumeroBeneficio(rsParceiroBeneficio.getString("numerobeneficio"));				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);
		return parceiroBeneficio;

	}

	public ParceiroBeneficio buscaParceiroBeneficioByEmOrNo(Long empresa, Long organizacao, String nome) {

		String sql = sqlParceiroBeneficio;

		if (empresa != null)
			sql += " AND PARCEIROBENEFICIO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND PARCEIROBENEFICIO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (PARCEIROBENEFICIO.nome like ?)";

		this.conn = this.conexao.getConexao();

		ParceiroBeneficio beneficio = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsParceiroBeneficio = this.stmt.executeQuery();

			while (rsParceiroBeneficio.next()) {
				beneficio = new ParceiroBeneficio();
				beneficio.setParceiroBeneficio_id(rsParceiroBeneficio.getLong("parceirobeneficio_id"));
				beneficio.setNumeroBeneficio(rsParceiroBeneficio.getString("numeroBeneficio"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsParceiroBeneficio, stmt, conn);

		return beneficio;

	}

}