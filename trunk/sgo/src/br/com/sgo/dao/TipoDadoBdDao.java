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
import br.com.sgo.modelo.TipoDadoBd;

@Component
public class TipoDadoBdDao extends Dao<TipoDadoBd> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTiposDado;

	public TipoDadoBdDao(Session session, ConnJDBC conexao) {
		super(session, TipoDadoBd.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<TipoDadoBd> buscaTiposDado(String nome){

		String sql = "select TIPODADOBD.tipodadobd_id, TIPODADOBD.nome from TIPODADOBD (NOLOCK) " +
				"	WHERE TIPODADOBD.nome like ? ";
		this.conn = this.conexao.getConexao();

		Collection<TipoDadoBd> tiposDadoBd = new ArrayList<TipoDadoBd>();
		try {

			this.stmt = conn.prepareStatement(sql);			

			this.stmt.setString(1,"%"+  nome + "%");			
			
			this.rsTiposDado = this.stmt.executeQuery();

			while (rsTiposDado.next()) {
				TipoDadoBd tipoDadoBd = new TipoDadoBd();

				tipoDadoBd.setTipoDadoBd_id(rsTiposDado.getLong("tipodadobd_id"));				
				tipoDadoBd.setNome(rsTiposDado.getString("nome"));

				tiposDadoBd.add(tipoDadoBd);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposDado, stmt, conn);

		return tiposDadoBd;

	}
	
	public Collection<TipoDadoBd> buscaTiposDadosLista(Long empresa_id, Long organizacao_id, String nome){
		
		String sql = "SELECT EMPRESA.nome as empresa_nome , EMPRESA.empresa_id , " +
				"			 ORGANIZACAO.nome as organizacao_nome, ORGANIZACAO.organizacao_id , " +
				"			 TIPODADOBD.nome as tipoDadoBd_nome , " +
				"			 TIPODADOBD.chave as tipoDadoBd_chave, TIPODADOBD.tipodadobd_id " +
				"		FROM (EMPRESA (NOLOCK) INNER JOIN TIPODADOBD (NOLOCK) ON EMPRESA.empresa_id = TIPODADOBD.empresa_id) " +
				"		INNER JOIN ORGANIZACAO (NOLOCK) ON TIPODADOBD.organizacao_id = ORGANIZACAO.organizacao_id WHERE ";

			if(empresa_id != null)
				sql +=	" EMPRESA.empresa_id = ? ";

			if(organizacao_id != null)
				sql += 	" AND ORGANIZACAO.organizacao_id = ?  ";

			if(!nome.equals(""))
				sql +=	" AND TIPODADOBD.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoDadoBd> tiposDadoBd = new ArrayList<TipoDadoBd>();

		try {

			this.stmt = conn.prepareStatement(sql);			

			if(empresa_id != null)
				this.stmt.setLong(1,empresa_id);

			if(organizacao_id != null)
				this.stmt.setLong(2, organizacao_id);

			if(!nome.equals(""))
				this.stmt.setString(3,"%" + nome + "%");

			this.rsTiposDado = this.stmt.executeQuery();

			while (rsTiposDado.next()) {			

				TipoDadoBd tipoDadoBd = new TipoDadoBd();
				Empresa e = new Empresa();
				Organizacao o = new Organizacao();

				e.setEmpresa_id(rsTiposDado.getLong("empresa_id"));
				e.setNome(rsTiposDado.getString("empresa_nome"));
				o.setOrganizacao_id(rsTiposDado.getLong("organizacao_id"));
				o.setNome(rsTiposDado.getString("organizacao_nome"));

				tipoDadoBd.setTipoDadoBd_id(rsTiposDado.getLong("tipodadobd_id"));				
				tipoDadoBd.setNome(rsTiposDado.getString("tipoDadoBd_nome"));
				tipoDadoBd.setChave(rsTiposDado.getString("tipoDadoBd_chave"));
				tipoDadoBd.setEmpresa(e);
				tipoDadoBd.setOrganizacao(o);

				tiposDadoBd.add(tipoDadoBd);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposDado, stmt, conn);

		return tiposDadoBd;

	}

}
