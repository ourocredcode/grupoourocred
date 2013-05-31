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

@Entity
@Component
@Table(name = "WORKFLOWTRANSICAO")
public class WorkflowTransicao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "workflowtransicao_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long workflowTransicao_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = false)
	private Workflow workflow;
	
	@ManyToOne
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowEtapa;

	@ManyToOne
	@JoinColumn(name = "workflowetapaproximo_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowEtapaProximo;

	@ManyToOne
	@JoinColumn(name = "perfil_id", updatable = true, nullable = true)
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "sequencia")
	private Integer sequencia;

	@Column(name = "ispadrao")
	private Boolean isPadrao;

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

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getWorkflowTransicao_id() {
		return workflowTransicao_id;
	}

	public void setWorkflowTransicao_id(Long workflowTransicao_id) {
		this.workflowTransicao_id = workflowTransicao_id;
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

	public WorkflowEtapa getWorkflowEtapa() {
		return workflowEtapa;
	}

	public void setWorkflowEtapa(WorkflowEtapa workflowEtapa) {
		this.workflowEtapa = workflowEtapa;
	}

	public WorkflowEtapa getWorkflowEtapaProximo() {
		return workflowEtapaProximo;
	}

	public void setWorkflowEtapaProximo(WorkflowEtapa workflowEtapaProximo) {
		this.workflowEtapaProximo = workflowEtapaProximo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSequencia() {
		return sequencia;
	}

	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}
}