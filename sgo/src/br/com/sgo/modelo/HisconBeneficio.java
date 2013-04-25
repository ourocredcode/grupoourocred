package br.com.sgo.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name = "HISCONBENEFICIO")
public class HisconBeneficio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "hisconbeneficio_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long hisconBeneficio_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "parceirobeneficio_id", updatable = true, nullable = false)
	private ParceiroBeneficio parceiroBeneficio;

	@ManyToOne
	@JoinColumn(name = "usuario_id", updatable = true, nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowEtapa;

	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = false)
	private Workflow workflow;

	@ManyToOne
	@JoinColumn(name = "workflowposicao_id", updatable = true, nullable = false)
	private Workflow workflowPosicao;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "dataadm")
	private Calendar dataAdm;

	@Column(name = "dataenvio")
	private Calendar dataEnvio;

	@Column(name = "nome")
	private String nome;

	@Column(name = "caminhoarquivo")
	private String caminhoArquivo;

	@Column(name = "isworkflow")
	private Boolean isWorkflow;

	@Column(name = "isenviado")
	private Boolean isEnviado;

	@Column(name = "isimportado")
	private Boolean isImportado;

	@Column(name = "ispadrao")
	private Boolean isPadrao;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getHisconBeneficio_id() {
		return hisconBeneficio_id;
	}

	public void setHisconBeneficio_id(Long hisconBeneficio_id) {
		this.hisconBeneficio_id = hisconBeneficio_id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public ParceiroBeneficio getParceiroBeneficio() {
		return parceiroBeneficio;
	}

	public void setParceiroBeneficio(ParceiroBeneficio parceiroBeneficio) {
		this.parceiroBeneficio = parceiroBeneficio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public WorkflowEtapa getWorkflowEtapa() {
		return workflowEtapa;
	}

	public void setWorkflowEtapa(WorkflowEtapa workflowEtapa) {
		this.workflowEtapa = workflowEtapa;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Workflow getWorkflowPosicao() {
		return workflowPosicao;
	}

	public void setWorkflowPosicao(Workflow workflowPosicao) {
		this.workflowPosicao = workflowPosicao;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public Calendar getDataAdm() {
		return dataAdm;
	}

	public void setDataAdm(Calendar dataAdm) {
		this.dataAdm = dataAdm;
	}

	public Calendar getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Calendar dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public Boolean getIsWorkflow() {
		return isWorkflow;
	}

	public void setIsWorkflow(Boolean isWorkflow) {
		this.isWorkflow = isWorkflow;
	}

	public Boolean getIsEnviado() {
		return isEnviado;
	}

	public void setIsEnviado(Boolean isEnviado) {
		this.isEnviado = isEnviado;
	}

	public Boolean getIsImportado() {
		return isImportado;
	}

	public void setIsImportado(Boolean isImportado) {
		this.isImportado = isImportado;
	}

	public Boolean getIsPadrao() {
		return isPadrao;
	}

	public void setIsPadrao(Boolean isPadrao) {
		this.isPadrao = isPadrao;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}