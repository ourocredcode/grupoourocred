package br.com.sgo.modelo;

import java.io.Serializable;

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
@Table(name = "WORKFLOWETAPA")
public class WorkflowEtapa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "workflowetapa_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long workflowEtapa_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = false)
	private Workflow workflow;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "ordemetapa")
	private Integer ordemEtapa;

	@Column(name = "ispadrao")
	private Boolean isPadrao;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getWorkflowEtapa_id() {
		return workflowEtapa_id;
	}

	public void setWorkflowEtapa_id(Long workflowEtapa_id) {
		this.workflowEtapa_id = workflowEtapa_id;
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

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
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

	public Integer getOrdemEtapa() {
		return ordemEtapa;
	}

	public void setOrdemEtapa(Integer ordemEtapa) {
		this.ordemEtapa = ordemEtapa;
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