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

import br.com.sgo.modelo.CategoriaParceiro;
import br.com.sgo.modelo.ClassificacaoParceiro;
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.GrupoParceiro;
import br.com.sgo.modelo.Janela;
import br.com.sgo.modelo.Organizacao;

@Component
public class FuncaoDao extends Dao<Funcao> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFuncao;
	
	public FuncaoDao(Session session,ConnJDBC conexao) {
		super(session, Funcao.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Collection<Funcao> buscaFuncoes(Long empresa_id, Long organizacao_id, String nome){

		String sql = "select FUNCAO.funcao_id, FUNCAO.nome from FUNCAO (NOLOCK) WHERE FUNCAO.empresa_id = ? AND FUNCAO.organizacao_id = ? AND FUNCAO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Funcao> funcoes = new ArrayList<Funcao>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,"%"+  nome + "%");			
			this.rsFuncao = this.stmt.executeQuery();

			while (rsFuncao.next()) {
				Funcao funcao = new Funcao();

				funcao.setFuncao_id(rsFuncao.getLong("funcao_id"));				
				funcao.setNome(rsFuncao.getString("nome"));

				funcoes.add(funcao);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsFuncao, stmt, conn);

		return funcoes;

	}

}