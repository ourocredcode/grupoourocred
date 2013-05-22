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
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.WorkflowEtapa;

@Component
public class ControleFormularioDao extends Dao<ControleFormulario> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsControleFormulario;
	
	private String sqlControle = " SELECT " + 
								 " CONTROLEFORMULARIO.controleformulario_id, CONTROLEFORMULARIO.tipocontrole_id, " +     
								 " TIPOCONTROLE.nome as tipocontrole_nome,   CONTROLEFORMULARIO.formulario_id ,      " +
								 " CONTROLEFORMULARIO.usuario_id, USUARIO.nome as usuario_nome, " +    
								 " CONTROLEFORMULARIO.dataatuacao , CONTROLEFORMULARIO.confirmaprazo, CONTROLEFORMULARIO.quantidadecontrato, " +   
								 " CONTROLEFORMULARIO.valorliquido, CONTROLEFORMULARIO.valorparcela, " + 
								 " ETAPA.nome as workflow_nome ,ETAPA.workflowetapa_id  , " +
								 " MOTIVO.nome as workflowpendencia_nome, MOTIVO.workflowetapa_id as workflowetapapendencia_id " +
								 " FROM ((((CONTROLEFORMULARIO (NOLOCK) " +      
								 " INNER JOIN TIPOCONTROLE (NOLOCK) ON CONTROLEFORMULARIO.tipocontrole_id = TIPOCONTROLE.tipocontrole_id) " +       
								 " INNER JOIN FORMULARIO (NOLOCK) ON CONTROLEFORMULARIO.formulario_id = FORMULARIO.formulario_id) " +       
								 " INNER JOIN USUARIO (NOLOCK) ON CONTROLEFORMULARIO.usuario_id = USUARIO.usuario_id) " +  
								 " INNER JOIN WORKFLOWETAPA AS ETAPA (NOLOCK) ON ETAPA.workflowetapa_id = CONTROLEFORMULARIO.workflowetapa_id) " +
								 " LEFT JOIN WORKFLOWETAPA AS MOTIVO (NOLOCK) ON MOTIVO.workflowetapa_id = CONTROLEFORMULARIO.workflowetapapendencia_id ";

	public ControleFormularioDao(Session session, ConnJDBC conexao) {
		super(session, ControleFormulario.class);
		this.session = session;
		this.conexao = conexao;
	}
	
	public ControleFormulario buscaControleByContratoTipoControle(Long formulario_id, Long tipocontrole_id) {

		String sql = sqlControle;

		if (formulario_id != null)
			sql += " WHERE FORMULARIO.formulario_id = ? ";
		
		if (tipocontrole_id != null)
			sql += " AND TIPOCONTROLE.tipocontrole_id = ?";

		this.conn = this.conexao.getConexao();
		
		ControleFormulario controleFormulario = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,formulario_id);
			this.stmt.setLong(2,tipocontrole_id);

			this.rsControleFormulario = this.stmt.executeQuery();

			while (rsControleFormulario.next()) {

				controleFormulario = new ControleFormulario();

				Usuario usuario = new Usuario();
				Formulario formulario = new Formulario();
				Calendar calendarAux = new GregorianCalendar();
				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				WorkflowEtapa workflowEtapaPendencia = new WorkflowEtapa();

				usuario.setUsuario_id(rsControleFormulario.getLong("usuario_id"));
				usuario.setNome(rsControleFormulario.getString("usuario_nome"));
				
				workflowEtapa.setWorkflowEtapa_id(rsControleFormulario.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsControleFormulario.getString("workflow_nome"));

				workflowEtapaPendencia.setWorkflowEtapa_id(rsControleFormulario.getLong("workflowetapapendencia_id"));
				workflowEtapaPendencia.setNome(rsControleFormulario.getString("workflowpendencia_nome"));

				controleFormulario.setControleFormulario_id(rsControleFormulario.getLong("controleformulario_id"));
				controleFormulario.setQuantidadeContrato(rsControleFormulario.getInt("quantidadecontrato"));
				controleFormulario.setConfirmaPrazo(rsControleFormulario.getBoolean("confirmaprazo"));
				controleFormulario.setValorLiquido(rsControleFormulario.getDouble("valorliquido"));
				controleFormulario.setValorParcela(rsControleFormulario.getDouble("valorparcela"));
				controleFormulario.setFormulario(formulario);
				controleFormulario.setUsuario(usuario);
				controleFormulario.setWorkflowEtapa(workflowEtapa);
				controleFormulario.setWorkflowEtapaPendencia(workflowEtapaPendencia);

				if(rsControleFormulario.getDate("dataatuacao") != null){
					calendarAux.setTime(rsControleFormulario.getDate("dataatuacao"));
					controleFormulario.setDataAtuacao(calendarAux);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsControleFormulario, stmt, conn);
		return controleFormulario;

	}

}