package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.CustomDateUtil;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Cidade;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.TipoLocalidade;
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.Usuario;

@Component
public class LogisticaDao extends Dao<Logistica> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsLogistica;

	private final String sqlLogistica = " SELECT LOGISTICA.logistica_id, LOGISTICA.contrato_id, LOGISTICA.tipologistica_id, " +
			"									 TIPOLOGISTICA.nome as tipoLogistica_nome, " +
			"									 LOGISTICA.periodo_id, PERIODO.nome as periodo_nome , LOGISTICA.parceironegociorepresentante_id, " +
			"									 PARCEIRONEGOCIO.nome as parceiro_nome, LOGISTICA.dataassinatura" +
			"				 				FROM " +
			"									(((LOGISTICA INNER JOIN CONTRATO ON LOGISTICA.contrato_id = CONTRATO.contrato_id) INNER JOIN PERIODO ON LOGISTICA.periodo_id = PERIODO.periodo_id) " +
			"										 INNER JOIN TIPOLOGISTICA ON LOGISTICA.tipologistica_id = TIPOLOGISTICA.tipologistica_id) " +
			"										 LEFT JOIN PARCEIRONEGOCIO ON LOGISTICA.parceironegociorepresentante_id = PARCEIRONEGOCIO.parceironegocio_id  ";

	public LogisticaDao(Session session, ConnJDBC conexao) {

		super(session, Logistica.class);
		this.conexao = conexao;

	}
	
	public Collection<Logistica> buscaLogisticaByContrato(Long contrato_id) {

		String sql = sqlLogistica;

		if(contrato_id != null)
			sql += " WHERE LOGISTICA.contrato_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Logistica> logisticas = new ArrayList<Logistica>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, contrato_id);

			this.rsLogistica = this.stmt.executeQuery();

			while (rsLogistica.next()) {
				
				Logistica logistica = new Logistica();
				Contrato contrato = new Contrato();
				ParceiroNegocio representante = new ParceiroNegocio();
				Periodo periodo = new Periodo();
				TipoLogistica tipoLogistica = new TipoLogistica();
				Calendar dataAssinatura = new GregorianCalendar();
				
				logistica.setLogistica_id(rsLogistica.getLong("logistica_id"));
				contrato.setContrato_id(rsLogistica.getLong("contrato_id"));
				representante.setParceiroNegocio_id(rsLogistica.getLong("parceironegociorepresentante_id"));
				representante.setNome(rsLogistica.getString("parceiro_nome"));
				periodo.setPeriodo_id(rsLogistica.getLong("periodo_id"));
				periodo.setNome(rsLogistica.getString("periodo_nome"));
				tipoLogistica.setTipoLogistica_id(rsLogistica.getLong("tipologistica_id"));
				tipoLogistica.setNome(rsLogistica.getString("tipoLogistica_nome"));
				dataAssinatura.setTime(rsLogistica.getDate("dataassinatura"));

				logistica.setContrato(contrato);
				logistica.setParceiroNegocioRepresentante(representante);
				logistica.setPeriodo(periodo);
				logistica.setTipoLogistica(tipoLogistica);
				logistica.setDataAssinatura(dataAssinatura);

				logisticas.add(logistica);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsLogistica, stmt, conn);
		return logisticas;
	}
	
	
	public Collection<Logistica> buscaLogisticaByDataAssinatura(Long empresa_id, Long organizacao_id, Collection<Usuario> consultores,Boolean isSupervisorApoio, 
			Calendar calAssinaturaInicio, Calendar calAssinaturaFim, Collection<Long> bancos,Collection<String> status,Long tipoLogistica_id, Long periodo_id, Long regiao_id) {

		String sql = " SELECT " +
			" DISTINCT " +
			" EMPRESA.nome as empresa_nome, " +
			" ORGANIZACAO.nome as organizacao_nome, " +
			" LOGISTICA.dataassinatura as data_assinatura, " +
			" SUPERVISOR.apelido as super_apelido, " +
			" USUARIO.apelido as user_apelido, " +
			" LOGISTICA.horaassinaturainicio as hora_assinatura_inicio, " +
			" LOGISTICA.horaassinaturafim as hora_assinatura_fim," +
			" PERIODO.nome as periodo_nome , " +
			" PARCEIROCONTATO.nome as cliente_telefone, " +
			" PARCEIRONEGOCIO.nome as cliente_nome, " +
			" PARCEIROBENEFICIO.numerobeneficio as cliente_beneficio, " +
			" TIPOLOCALIDADE.nome as endereco_tipo, " +
			" LOCALIDADE.endereco as endereco_nome, " +
			" LOCALIDADE.bairro as endereco_bairro, " +
			" LOCALIDADE.cep as endereco_cep, " +
			" PARCEIROLOCALIDADE.numero, " +
			" PARCEIROLOCALIDADE.pontoreferencia, " +
			" PARCEIROLOCALIDADE.complemento," +
			" CIDADE.nome as endereco_cidade, " +
			" CAST ( COUNT(CONTRATO.contrato_id) AS VARCHAR ) + ' - ' + BANCO.nome as descricao, " +
			" SUM(CONTRATO.valormeta) as valor_meta " +
		" FROM  " +   
		" EMPRESA (NOLOCK) " + 
		" INNER JOIN ORGANIZACAO (NOLOCK) ON ORGANIZACAO.empresa_id = EMPRESA.empresa_id " +
		" INNER JOIN FORMULARIO (NOLOCK) ON FORMULARIO.organizacao_id = ORGANIZACAO.organizacao_id " +
		" INNER JOIN CONTRATO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id " +
		" INNER JOIN BANCO (NOLOCK) ON BANCO.banco_id = CONTRATO.banco_id " +
		" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id " +
		" INNER JOIN USUARIO AS SUPERVISOR (NOLOCK) ON USUARIO.supervisor_usuario_id = SUPERVISOR.usuario_id " +
		" INNER JOIN LOGISTICA (NOLOCK) ON LOGISTICA.logistica_id = ( SELECT max(LOGISTICA.logistica_id) FROM LOGISTICA (NOLOCK) WHERE LOGISTICA.contrato_id = CONTRATO.contrato_id AND LOGISTICA.isactive = 1 ) " +
		" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIRONEGOCIO.parceironegocio_id = FORMULARIO.parceironegocio_id " +
		" INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id AND PARCEIROBENEFICIO.numerobeneficio = CONTRATO.numerobeneficio " +
		" INNER JOIN PARCEIROLOCALIDADE (NOLOCK) ON PARCEIROLOCALIDADE.parceirolocalidade_id = ( SELECT max(PARCEIROLOCALIDADE.parceirolocalidade_id) FROM PARCEIROLOCALIDADE (NOLOCK) WHERE PARCEIROLOCALIDADE.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id AND PARCEIROLOCALIDADE.isactive = 1 ) " +
		" INNER JOIN PARCEIROCONTATO (NOLOCK) ON PARCEIROCONTATO.parceirocontato_id = ( SELECT max(PARCEIROCONTATO.parceirocontato_id) FROM PARCEIROCONTATO (NOLOCK) WHERE PARCEIROCONTATO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id AND PARCEIROCONTATO.isactive = 1 ) " +
		" INNER JOIN LOCALIDADE (NOLOCK) ON PARCEIROLOCALIDADE.localidade_id = LOCALIDADE.localidade_id AND PARCEIROLOCALIDADE.isactive = 1 " +
		" INNER JOIN CIDADE (NOLOCK) ON LOCALIDADE.cidade_id = CIDADE.cidade_id" + 
		" INNER JOIN REGIAO(NOLOCK) ON REGIAO.regiao_id = CIDADE.regiao_id  " +
		" INNER JOIN TIPOENDERECO (NOLOCK) ON PARCEIROLOCALIDADE.tipoendereco_id = TIPOENDERECO.tipoendereco_id AND TIPOENDERECO.nome like 'Assinatura' " +
		" INNER JOIN TIPOLOCALIDADE (NOLOCK) ON TIPOLOCALIDADE.tipolocalidade_id = LOCALIDADE.tipolocalidade_id " +
		" INNER JOIN PERIODO (NOLOCK) ON PERIODO.periodo_id = LOGISTICA.periodo_id " +
		" WHERE " +
		" EMPRESA.empresa_id = ? " +
		" AND ORGANIZACAO.organizacao_id = ? ";

		String clause = "";
		int x = 0;
		sql += " AND ( 1=1 ";

		for(Usuario u : consultores){

			clause = x <= 0 ? "AND" : "OR";

			if(u != null) {

				if(isSupervisorApoio)
					sql += clause + " ( USUARIO.usuario_id = ? ) ";
				else
					sql += clause + " ( USUARIO.usuario_id = ? OR SUPERVISOR.usuario_id = ? ) ";

				x++;
				clause = "";

			}

		}
		
		sql += " ) ";
		
		if(calAssinaturaInicio != null)
		    sql += " AND ( LOGISTICA.dataassinatura BETWEEN ? AND ? ) ";

		

		 x = 0;

		sql += " AND ( 1=1 ";

		for(Long bancosAux1 : bancos){

			clause = x <= 0 ? "AND" : "OR";

			if(bancosAux1 != null){
				sql += clause + " ( CONTRATO.banco_id = ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(String statusAux1 : status){

			clause = x <= 0 ? "AND" : "OR";

			if(!statusAux1.equals("")){
				sql += clause + " ( ETAPA.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		if(tipoLogistica_id != null)
			sql += "  AND ( LOGISTICA.tipologistica_id = ? ) ";
		
		if(periodo_id != null)
			sql += "  AND ( LOGISTICA.periodo_id = ? ) ";
		
		if(regiao_id != null)
			sql += "  AND ( REGIAO.regiao_id = ? ) ";
		
		sql += "  GROUP BY " +
				"   EMPRESA.nome, " +
				"   LOGISTICA.dataassinatura, " +
				" 	ORGANIZACAO.nome, " +
				" 	SUPERVISOR.apelido, " +
				"	USUARIO.apelido, " +
				"	LOGISTICA.horaassinaturainicio, " +
				"	LOGISTICA.horaassinaturafim, " +
				"   PERIODO.nome, " +
				"	PARCEIROCONTATO.nome, " +
				"	PARCEIRONEGOCIO.nome, " +
				"	PARCEIROBENEFICIO.numerobeneficio, " +
				"	TIPOLOCALIDADE.nome, " +
				"	LOCALIDADE.endereco, " +
				"	LOCALIDADE.bairro, " +
				"	LOCALIDADE.cep, " +
				"   PARCEIROLOCALIDADE.numero, " +
				"   PARCEIROLOCALIDADE.pontoreferencia, " +
				"   PARCEIROLOCALIDADE.complemento," +
				"   BANCO.nome, " +
				"	CIDADE.nome " +
				" ORDER BY SUPERVISOR.apelido ";

		this.conn = this.conexao.getConexao();

		Collection<Logistica> logisticas = new ArrayList<Logistica>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println(sql);
			
			int curr = 1;

			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}

			for(Usuario u : consultores){

				if(u != null) {

					if(isSupervisorApoio) {

						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;

					} else {

						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;
						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;

					}

				}

			}

			if(calAssinaturaInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calAssinaturaInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calAssinaturaFim).getTimeInMillis()));
				curr++;

			} 

			for(Long bancosAux2 : bancos){

				if(bancosAux2 != null) {
					this.stmt.setLong(curr, bancosAux2);
					curr++;
				}

			}
			
			for(String statusAux2 : status){

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
					curr++;
				}

			}
			
			if(tipoLogistica_id != null) {

				//System.out.println(" tipoLogistica " + tipoLogistica_id );

				this.stmt.setLong(curr, tipoLogistica_id);
				curr++;
				
			}
			
			if(periodo_id != null) {

				//System.out.println(" periodo " + periodo_id );

				this.stmt.setLong(curr, periodo_id);
				curr++;

			}
			
			if(regiao_id != null) {

				//System.out.println(" regiao_id " + regiao_id );

				this.stmt.setLong(curr, regiao_id);
				curr++;

			}

			this.rsLogistica = this.stmt.executeQuery();

			while (rsLogistica.next()) {

				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Logistica logistica = new Logistica();
				Periodo periodo = new Periodo();
				Usuario supervisor = new Usuario();
				Usuario user = new Usuario();
				ParceiroNegocio cliente = new ParceiroNegocio();
				ParceiroBeneficio clienteBeneficio = new ParceiroBeneficio();
				ParceiroLocalidade clienteEndereco = new ParceiroLocalidade(); 
				TipoLocalidade tl = new TipoLocalidade();
				Localidade localidade = new Localidade();
				Cidade cidade = new Cidade();
				Formulario formulario = new Formulario();
				Contrato contrato = new Contrato();
				Calendar dataAssinatura = new GregorianCalendar();

				empresa.setNome(rsLogistica.getString("empresa_nome"));
				organizacao.setNome(rsLogistica.getString("organizacao_nome"));

				cliente.setNome(rsLogistica.getString("cliente_nome"));
				cliente.setDescricao(rsLogistica.getString("cliente_telefone"));

				clienteBeneficio.setNumeroBeneficio(rsLogistica.getString("cliente_beneficio"));
				
				periodo.setNome(rsLogistica.getString("periodo_nome"));
				
				tl.setNome(rsLogistica.getString("endereco_tipo"));
				cidade.setNome(rsLogistica.getString("endereco_cidade"));
				localidade.setEndereco(rsLogistica.getString("endereco_nome"));
				localidade.setBairro(rsLogistica.getString("endereco_bairro"));
				localidade.setCep(rsLogistica.getString("endereco_cep"));
				localidade.setTipoLocalidade(tl);
				localidade.setCidade(cidade);

				clienteEndereco.setNumero(rsLogistica.getString("numero"));
				clienteEndereco.setComplemento(rsLogistica.getString("complemento"));
				clienteEndereco.setPontoReferencia(rsLogistica.getString("pontoreferencia"));
				clienteEndereco.setLocalidade(localidade);

				formulario.setParceiroNegocio(cliente);
				formulario.setParceiroLocalidade(clienteEndereco);
				formulario.setParceiroBeneficio(clienteBeneficio);

				supervisor.setApelido(rsLogistica.getString("super_apelido"));
				user.setApelido(rsLogistica.getString("user_apelido"));
				user.setSupervisorUsuario(supervisor);

				contrato.setValorMeta(rsLogistica.getDouble("valor_meta"));
				contrato.setFormulario(formulario);
				contrato.setUsuario(user);

				if(rsLogistica.getDate("data_assinatura") != null) {
					dataAssinatura.setTime(rsLogistica.getDate("data_assinatura"));
					logistica.setDataAssinatura(dataAssinatura);
				}
				
				if(rsLogistica.getTimestamp("hora_assinatura_inicio") != null){

					Calendar horaAssinaturaInicio = new GregorianCalendar();
					horaAssinaturaInicio.setTime(rsLogistica.getTimestamp("hora_assinatura_inicio"));
					logistica.setHoraAssinaturaInicio(horaAssinaturaInicio);
					
				} else {
					
					logistica.setHoraAssinaturaInicio(dataAssinatura);
				}
				
				if(rsLogistica.getTimestamp("hora_assinatura_fim") != null){
					
					Calendar horaAssinaturaFim = new GregorianCalendar();
					horaAssinaturaFim.setTime(rsLogistica.getTimestamp("hora_assinatura_fim"));
					logistica.setHoraAssinaturaFim(horaAssinaturaFim);
					
				} else {
					
					logistica.setHoraAssinaturaFim(dataAssinatura);
					
				}

				logistica.setPeriodo(periodo);
				logistica.setContrato(contrato);
				logistica.setDescricao(rsLogistica.getString("descricao"));

				logisticas.add(logistica);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsLogistica, stmt, conn);
		
		return logisticas;

	}
	
	

}
