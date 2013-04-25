package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;

@Component
public class ContratoDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContrato;

	private static final String sqlContrato = " SELECT CONTRATO.empresa_id, EMPRESA.nome, CONTRATO.organizacao_id, ORGANIZACAO.nome, " +
			"					FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, CONTRATO.coeficiente_id, " +
			"					CONTRATO.produto_id, CONTRATO.tabela_id, " +
			"					CONTRATO.banco_id, CONTRATO.recompra_banco_id, CONTRATO.usuario_id, CONTRATO.prazo, " +
			"					CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, " +
			"					CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, " +
			"					CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto ," +
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
			"						 INNER JOIN BANCO AS B2 (NOLOCK) ON CONTRATO.recompra_banco_id = B2.banco_id ";

	public ContratoDao(Session session, ConnJDBC conexao) {
		super(session, Contrato.class);
		this.conexao = conexao;
	}

	public Collection<Contrato> buscaContratoByUsuario(Long usuario_id) {

		String sql = sqlContrato;
		
		if(usuario_id != null)
			sql += " WHERE USUARIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, usuario_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				Formulario formulario = new Formulario();
				formulario.setFormulario_id(rsContrato.getLong("formulario_id"));

				Calendar created = new GregorianCalendar();
				created.setTime(rsContrato.getDate("created"));

				formulario.setCreated(created);

				Contrato contrato = new Contrato();
				contrato.setContrato_id(rsContrato.getLong("contrato_id"));

				ParceiroNegocio parceiro = new ParceiroNegocio();
				parceiro.setParceiroNegocio_id(rsContrato.getLong("parceironegocio_id"));
				parceiro.setNome(rsContrato.getString("parceiro_nome"));
				parceiro.setCpf(rsContrato.getString("parceiro_cpf"));

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsContrato.getLong("coeficiente_id"));
				coeficiente.setValor(rsContrato.getDouble("valor"));

				Produto produto = new Produto();
				produto.setProduto_id(rsContrato.getLong("produto_id"));
				produto.setNome(rsContrato.getString("produto_nome"));

				Banco b1 = new Banco();
				b1.setBanco_id(rsContrato.getLong("banco_id"));
				b1.setNome(rsContrato.getString("banco_nome"));

				Banco b2 = new Banco();
				b2.setBanco_id(rsContrato.getLong("recompra_banco_id"));
				b2.setNome(rsContrato.getString("bancoRecompra_nome"));

				formulario.setParceiroNegocio(parceiro);
				contrato.setFormulario(formulario);
				contrato.setCoeficiente(coeficiente);
				contrato.setProduto(produto);
				//contrato.setBanco(b1);
				//contrato.setRecompraBanco(b2);

				contrato.setValorContrato(rsContrato.getDouble("valorcontrato"));
				contrato.setValorDivida(rsContrato.getDouble("valordivida"));
				contrato.setValorLiquido(rsContrato.getDouble("valorliquido"));
				contrato.setValorMeta(rsContrato.getDouble("valormeta"));
				contrato.setValorParcela(rsContrato.getDouble("valorparcela"));
				contrato.setValorSeguro(rsContrato.getDouble("valorseguro"));
				contrato.setPrazo(rsContrato.getInt("prazo"));
				contrato.setQtdParcelasAberto(rsContrato.getInt("qtdparcelasaberto"));

				contratos.add(contrato);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

}
