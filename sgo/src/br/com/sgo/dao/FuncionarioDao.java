package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Departamento;
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.Funcionario;

@Component
public class FuncionarioDao extends Dao<Funcionario> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFuncionario;

	public FuncionarioDao(Session session, ConnJDBC conexao) {
		super(session, Funcionario.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public Funcionario buscaFuncionarioPorParceiroNegocio(Long parceironegocio_id){

		String sql = "SELECT FUNCIONARIO.empresa_id, " +
				"				EMPRESA.nome, FUNCIONARIO.organizacao_id, ORGANIZACAO.nome, PARCEIRONEGOCIO.parceironegocio_id, " +
				"				PARCEIRONEGOCIO.nome, FUNCIONARIO.funcao_id, FUNCAO.nome as funcao_nome, FUNCIONARIO.departamento_id, FUNCIONARIO.funcionario_id," +
				"				DEPARTAMENTO.nome as departamento_nome FROM " +
				"				(((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN PARCEIRONEGOCIO (NOLOCK)  " +
				"					ON EMPRESA.empresa_id = PARCEIRONEGOCIO.empresa_id) " +
				"					ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id)  " +
				"				INNER JOIN FUNCIONARIO (NOLOCK) ON PARCEIRONEGOCIO.parceironegocio_id = FUNCIONARIO.parceironegocio_id)  " +
				"				INNER JOIN DEPARTAMENTO (NOLOCK) ON FUNCIONARIO.departamento_id = DEPARTAMENTO.departamento_id)  " +
				"				INNER JOIN FUNCAO (NOLOCK) ON FUNCIONARIO.funcao_id = FUNCAO.funcao_id WHERE PARCEIRONEGOCIO.parceironegocio_id = ?";

		this.conn = this.conexao.getConexao();
		Funcionario funcionario = new Funcionario();

		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1,parceironegocio_id);			
			
			this.rsFuncionario = this.stmt.executeQuery();
			

			while (rsFuncionario.next()) {

				Departamento d = new Departamento();
				Funcao f = new Funcao();
				
				d.setDepartamento_id(rsFuncionario.getLong("departamento_id"));
				d.setNome(rsFuncionario.getString("departamento_nome"));

				f.setFuncao_id(rsFuncionario.getLong("funcao_id"));
				f.setNome(rsFuncionario.getString("funcao_nome"));
				
				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setFuncao(f);
				funcionario.setDepartamento(d);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);

		return funcionario;

	}

}
