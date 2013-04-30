package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Usuario;

@Component
public class FormularioDao extends Dao<Formulario> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFormulario;
	
	private static final String sqlFormulario = " SELECT CONTRATO.empresa_id, EMPRESA.nome, CONTRATO.organizacao_id, ORGANIZACAO.nome, " +
			"					FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, " +
			"					CONTRATO.coeficiente_id, " +
			"					CONTRATO.produto_id, CONTRATO.tabela_id, " +
			"					CONTRATO.banco_id, CONTRATO.recompra_banco_id, CONTRATO.usuario_id, USUARIO.nome as usuario_nome, CONTRATO.prazo, " +
			"					CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, " +
			"					CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, " +
			"					CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, " +
			"					B1.nome as banco_nome, B2.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor, " +
			"					PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf " +
			"					FROM " +
			"		((((((((CONTRATO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id) " +
			"						 INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id) " +
			"						 INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
			"						 INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id) " +
			"						 INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +
			"						 INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id) " +
			"						 INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id) " +
			"						 INNER JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id) " +
			"						 INNER JOIN BANCO AS B1 (NOLOCK) ON CONTRATO.banco_id = B1.banco_id" +
			"						 LEFT JOIN BANCO AS B2 (NOLOCK) ON CONTRATO.recompra_banco_id = B2.banco_id ";

	public FormularioDao(Session session, ConnJDBC conexao) {
		super(session, Formulario.class);
		this.conexao = conexao;
	}

	public Formulario buscaFormularioByContrato(Long contrato_id) {

		String sql = sqlFormulario;
		
		if(contrato_id != null)
			sql += " WHERE CONTRATO.contrato_id = ?  ";

		this.conn = this.conexao.getConexao();

		Formulario formulario = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, contrato_id);

			this.rsFormulario = this.stmt.executeQuery();

			while (rsFormulario.next()) {

				formulario = new Formulario();
				
				getFormulario(formulario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsFormulario, stmt, conn);

		return formulario;
	}	

	private void getFormulario(Formulario formulario)
			throws SQLException {

		Calendar created = new GregorianCalendar();

		Usuario usuario = new Usuario();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Coeficiente coeficiente = new Coeficiente();
		Produto produto = new Produto();
		
		Banco b1 = new Banco();
		Banco b2 = new Banco();

		usuario.setUsuario_id(rsFormulario.getLong("usuario_id"));
		usuario.setNome(rsFormulario.getString("usuario_nome"));

		formulario.setFormulario_id(rsFormulario.getLong("formulario_id"));
		created.setTime(rsFormulario.getDate("created"));
		formulario.setCreated(created);
		
		parceiro.setParceiroNegocio_id(rsFormulario.getLong("parceironegocio_id"));
		parceiro.setNome(rsFormulario.getString("parceiro_nome"));
		parceiro.setCpf(rsFormulario.getString("parceiro_cpf"));
		
		coeficiente.setCoeficiente_id(rsFormulario.getLong("coeficiente_id"));
		coeficiente.setValor(rsFormulario.getDouble("valor"));
		
		produto.setProduto_id(rsFormulario.getLong("produto_id"));
		produto.setNome(rsFormulario.getString("produto_nome"));
		
		b1.setBanco_id(rsFormulario.getLong("banco_id"));
		b1.setNome(rsFormulario.getString("banco_nome"));
		
		b2.setBanco_id(rsFormulario.getLong("recompra_banco_id"));
		b2.setNome(rsFormulario.getString("bancoRecompra_nome"));

		formulario.setParceiroNegocio(parceiro);

	}

}
