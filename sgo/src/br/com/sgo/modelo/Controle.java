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
@Table(name = "CONTROLE")
public class Controle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "controle_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long controle_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "contrato_id", updatable = true, nullable = false)
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name = "usuario_id", updatable = true, nullable = true)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "perfil_id", updatable = true, nullable = true)
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "workflow_id", updatable = true, nullable = true)
	private Workflow workflow;
	
	@ManyToOne
	@JoinColumn(name = "workflowetapa_id", updatable = true, nullable = true)
	private WorkflowEtapa workflowEtapa;
	
	@ManyToOne
	@JoinColumn(name = "tipocontrole_id", updatable = true, nullable = true)
	private TipoControle tipoControle;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = false)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = false)
	private Usuario updatedBy;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "chave")
	private String chave;
	
	@Column(name = "value")
	private String value;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "isactive")
	private Boolean isActive;
	
	@Column(name="dataprimeiraatuacao")
	private Calendar dataPrimeiraAtuacao;

	@Column(name="dataatuacao")
	private Calendar dataAtuacao;

	@Column(name="dataproximaatuacao")
	private Calendar dataProximaAtuacao;

	@Column(name="dataprevisao")
	private Calendar dataPrevisao;

	@Column(name="datachegada")
	private Calendar dataChegada;

	@Column(name="datavencimento")
	private Calendar dataVencimento;

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

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Calendar getDataPrimeiraAtuacao() {
		return dataPrimeiraAtuacao;
	}

	public void setDataPrimeiraAtuacao(Calendar dataPrimeiraAtuacao) {
		this.dataPrimeiraAtuacao = dataPrimeiraAtuacao;
	}

	public Calendar getDataAtuacao() {
		return dataAtuacao;
	}

	public void setDataAtuacao(Calendar dataAtuacao) {
		this.dataAtuacao = dataAtuacao;
	}

	public Calendar getDataProximaAtuacao() {
		return dataProximaAtuacao;
	}

	public void setDataProximaAtuacao(Calendar dataProximaAtuacao) {
		this.dataProximaAtuacao = dataProximaAtuacao;
	}

	public Calendar getDataPrevisao() {
		return dataPrevisao;
	}

	public void setDataPrevisao(Calendar dataPrevisao) {
		this.dataPrevisao = dataPrevisao;
	}

	public Calendar getDataChegada() {
		return dataChegada;
	}

	public void setDataChegada(Calendar dataChegada) {
		this.dataChegada = dataChegada;
	}

	public Calendar getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Calendar dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public TipoControle getTipoControle() {
		return tipoControle;
	}

	public void setTipoControle(TipoControle tipoControle) {
		this.tipoControle = tipoControle;
	}

	public Long getControle_id() {
		return controle_id;
	}

	public void setControle_id(Long controle_id) {
		this.controle_id = controle_id;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
}
