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
@Table(name = "CONTROLEFORMULARIO")
public class ControleFormulario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "controleformulario_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long controleFormulario_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "formulario_id", updatable = true, nullable = false)
	private Formulario formulario;
	
	@ManyToOne
	@JoinColumn(name = "tipocontrole_id", updatable = true, nullable = true)
	private TipoControle tipoControle;

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
	@JoinColumn(name = "workflowpendencia_id", updatable = true, nullable = true)
	private Workflow workflowPendencia;

	@ManyToOne
	@JoinColumn(name = "etapa_id", updatable = true, nullable = true)
	private Etapa etapa;
	
	@ManyToOne
	@JoinColumn(name = "etapapendencia_id", updatable = true, nullable = true)
	private Etapa etapaPendencia;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@Column(name = "created")
	private Calendar created;

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

	@Column(name="dataatuacao")
	private Calendar dataAtuacao;
	
	@Column(name="quantidadecontrato")
	private Integer quantidadeContrato;
	
	@Column(name="confirmaprazo")
	private Boolean confirmaPrazo;
	
	@Column(name="valorliquido")
	private Double valorLiquido;

	@Column(name="valorparcela")
	private Double valorParcela;

	public Long getControleFormulario_id() {
		return controleFormulario_id;
	}

	public void setControleFormulario_id(Long controleFormulario_id) {
		this.controleFormulario_id = controleFormulario_id;
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

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public TipoControle getTipoControle() {
		return tipoControle;
	}

	public void setTipoControle(TipoControle tipoControle) {
		this.tipoControle = tipoControle;
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

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
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

	public Calendar getDataAtuacao() {
		return dataAtuacao;
	}

	public void setDataAtuacao(Calendar dataAtuacao) {
		this.dataAtuacao = dataAtuacao;
	}

	public Integer getQuantidadeContrato() {
		return quantidadeContrato;
	}

	public void setQuantidadeContrato(Integer quantidadeContrato) {
		this.quantidadeContrato = quantidadeContrato;
	}

	public Boolean getConfirmaPrazo() {
		return confirmaPrazo;
	}

	public void setConfirmaPrazo(Boolean confirmaPrazo) {
		this.confirmaPrazo = confirmaPrazo;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}

}
