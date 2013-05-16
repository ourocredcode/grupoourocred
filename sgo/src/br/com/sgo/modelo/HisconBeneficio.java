package br.com.sgo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@JoinColumn(name = "perfil_id", updatable = true, nullable = false)
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = false)
	private Workflow workflow;

	@ManyToOne
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowEtapa;

	@ManyToOne
	@JoinColumn(name = "workflowposicao_id", updatable = true, nullable = false)
	private Workflow workflowPosicao;

	@ManyToOne
	@JoinColumn(name = "workflowposicaoetapa_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowPosicaoEtapa;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "dataadm")
	private Calendar dataAdm;

	@Column(name = "dataenvio")
	private Calendar dataEnvio;

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

	@Transient
	private Collection<WorkflowEtapa> workflowEtapas = new ArrayList<WorkflowEtapa>();

	@Transient
	private Integer countHiscons;

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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public WorkflowEtapa getWorkflowEtapa() {
		return workflowEtapa;
	}

	public void setWorkflowEtapa(WorkflowEtapa workflowEtapa) {
		this.workflowEtapa = workflowEtapa;
	}

	public Workflow getWorkflowPosicao() {
		return workflowPosicao;
	}

	public void setWorkflowPosicao(Workflow workflowPosicao) {
		this.workflowPosicao = workflowPosicao;
	}

	public WorkflowEtapa getWorkflowPosicaoEtapa() {
		return workflowPosicaoEtapa;
	}

	public void setWorkflowPosicaoEtapa(WorkflowEtapa workflowPosicaoEtapa) {
		this.workflowPosicaoEtapa = workflowPosicaoEtapa;
	}

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Usuario getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Usuario updatedBy) {
		this.updatedBy = updatedBy;
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
		return isEnviado == null ? false : isEnviado;
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

	public Integer getCountHiscons() {
		return countHiscons;
	}

	public void setCountHiscons(Integer countHiscons) {
		this.countHiscons = countHiscons;
	}

	public Collection<WorkflowEtapa> getWorkflowEtapas() {
		return workflowEtapas;
	}

	public void setWorkflowEtapas(Collection<WorkflowEtapa> workflowEtapas) {
		this.workflowEtapas = workflowEtapas;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}