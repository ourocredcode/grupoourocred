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
@Table(name = "PROCEDIMENTODETALHE")
public class ProcedimentoDetalhe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "procedimentodetalhe_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long procedimentoDetalhe_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "procedimento_id", updatable = true, nullable = true)
	private ProcedimentoConferencia procedimento;

	@ManyToOne
	@JoinColumn(name = "banco_id", updatable = true, nullable = true)
	private Banco banco;

	@ManyToOne
	@JoinColumn(name = "modeloprocedimento_id", updatable = true, nullable = true)
	private ModeloProcedimento modeloProcedimento;

	@ManyToOne
	@JoinColumn(name = "agente_id", updatable = true, nullable = true)
	private Agente agente;

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

	@Column(name = "acao")
	private Integer acao;

	@Column(name = "detalheprocedimento")
	private String detalheProcedimento;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getProcedimentoDetalhe_id() {
		return procedimentoDetalhe_id;
	}

	public void setProcedimentoDetalhe_id(Long procedimentoDetalhe_id) {
		this.procedimentoDetalhe_id = procedimentoDetalhe_id;
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

	public ProcedimentoConferencia getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(ProcedimentoConferencia procedimento) {
		this.procedimento = procedimento;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public ModeloProcedimento getModeloProcedimento() {
		return modeloProcedimento;
	}

	public void setModeloProcedimento(ModeloProcedimento modeloProcedimento) {
		this.modeloProcedimento = modeloProcedimento;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
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

	public Integer getAcao() {
		return acao;
	}

	public void setAcao(Integer acao) {
		this.acao = acao;
	}

	public String getDetalheProcedimento() {
		return detalheProcedimento;
	}

	public void setDetalheProcedimento(String detalheProcedimento) {
		this.detalheProcedimento = detalheProcedimento;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}