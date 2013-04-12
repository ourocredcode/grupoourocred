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
@Table(name = "WORKFLOW")
public class Workflow implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "workflow_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long workflow_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "tipoworkflow_id", updatable = true, nullable = false)
	private TipoWorkflow tipoWorkflow;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "etapainicial")
	private Integer etapaInicial;

	@Column(name = "ispadrao")
	private Boolean isPadrao;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(Long workflow_id) {
		this.workflow_id = workflow_id;
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

	public TipoWorkflow getTipoWorkflow() {
		return tipoWorkflow;
	}

	public void setTipoWorkflow(TipoWorkflow tipoWorkflow) {
		this.tipoWorkflow = tipoWorkflow;
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

	public Integer getEtapaInicial() {
		return etapaInicial;
	}

	public void setEtapaInicial(Integer etapaInicial) {
		this.etapaInicial = etapaInicial;
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