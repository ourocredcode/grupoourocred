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
@Table(name = "HISTORICOCONTROLEFORMULARIO")
public class HistoricoControleFormulario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "historicocontroleformulario_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long historicoControleFormulario_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "controleformulario_id", updatable = true, nullable = true)
	private ControleFormulario controleFormulario;
	
	@ManyToOne
	@JoinColumn(name = "formulario_id", updatable = true, nullable = true)
	private Formulario formulario;

	@ManyToOne
	@JoinColumn(name = "perfil_id", updatable = true, nullable = true)
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = true)
	private Workflow workflow;

	@ManyToOne
	@JoinColumn(name = "workflowpendencia_id", updatable = true, nullable = true)
	private Workflow workflowPendencia;

	@ManyToOne
	@JoinColumn(name = "etapa_id", updatable = true, nullable = true)
	private Etapa etapa;

	@ManyToOne
	@JoinColumn(name = "etapapendencia_id", updatable = true, nullable = true)
	private Etapa etapaPendencia;
	
	@Column(name = "created")
	private Calendar created;

	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;
	
	@Column(name = "isactive")
	private Boolean isActive;
	
	@Column(name = "updated")
	private Calendar updated;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "observacao")
	private String observacao;

	public Long getHistoricoControleFormulario_id() {
		return historicoControleFormulario_id;
	}

	public void setHistoricoControleFormulario_id(
			Long historicoControleFormulario_id) {
		this.historicoControleFormulario_id = historicoControleFormulario_id;
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

	public ControleFormulario getControleFormulario() {
		return controleFormulario;
	}

	public void setControleFormulario(ControleFormulario controleFormulario) {
		this.controleFormulario = controleFormulario;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
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

	public Workflow getWorkflowPendencia() {
		return workflowPendencia;
	}

	public void setWorkflowPendencia(Workflow workflowPendencia) {
		this.workflowPendencia = workflowPendencia;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Etapa getEtapaPendencia() {
		return etapaPendencia;
	}

	public void setEtapaPendencia(Etapa etapaPendencia) {
		this.etapaPendencia = etapaPendencia;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public Usuario getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Usuario updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}