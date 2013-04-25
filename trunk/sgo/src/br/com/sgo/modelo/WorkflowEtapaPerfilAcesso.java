package br.com.sgo.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;

@Component
@Table(name = "WORKFLOWETAPAPERFILACESSO")
public class WorkflowEtapaPerfilAcesso implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = false)
	private WorkflowEtapa workflowEtapa;

	@ManyToOne
	@JoinColumn(name = "perfil_id", updatable = true, nullable = false)
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@Column(name = "isleituraescrita")
	private Boolean isLeituraEscrita;
	
	@Column(name = "isactive")
	private Boolean isActive;

	public WorkflowEtapa getWorkflowEtapa() {
		return workflowEtapa;
	}

	public void setWorkflowEtapa(WorkflowEtapa workflowEtapa) {
		this.workflowEtapa = workflowEtapa;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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

	public Boolean getIsLeituraEscrita() {
		return isLeituraEscrita;
	}

	public void setIsLeituraEscrita(Boolean isLeituraEscrita) {
		this.isLeituraEscrita = isLeituraEscrita;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}