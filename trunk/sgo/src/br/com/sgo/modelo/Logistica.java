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
@Table(name = "LOGISTICA")
public class Logistica implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "logistica_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long logistica_id;

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
	@JoinColumn(name = "parceironegociorepresentante_id", updatable = true, nullable = false)
	private ParceiroNegocio parceiroNegocioRepresentante;

	@ManyToOne
	@JoinColumn(name = "tipologistica_id", updatable = true, nullable = false)
	private TipoLogistica tipoLogistica;

	@ManyToOne
	@JoinColumn(name = "periodos_id", updatable = true, nullable = false)
	private Periodos periodos;

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

	@Column(name = "dataassinatura")
	private Calendar dataAssinatura;

	@Column(name = "dataenvio")
	private Calendar dataEnvio;

	@Column(name = "nome")
	private String nome;

	@Column(name = "isfechado")
	private Boolean isFechado;

	@Column(name = "isexecutado")
	private Boolean isExecutado;

	@Column(name = "isassinado")
	private Boolean isAssinado;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getLogistica_id() {
		return logistica_id;
	}

	public void setLogistica_id(Long logistica_id) {
		this.logistica_id = logistica_id;
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

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public ParceiroNegocio getParceiroNegocioRepresentante() {
		return parceiroNegocioRepresentante;
	}

	public void setParceiroNegocioRepresentante(
			ParceiroNegocio parceiroNegocioRepresentante) {
		this.parceiroNegocioRepresentante = parceiroNegocioRepresentante;
	}

	public TipoLogistica getTipoLogistica() {
		return tipoLogistica;
	}

	public void setTipoLogistica(TipoLogistica tipoLogistica) {
		this.tipoLogistica = tipoLogistica;
	}

	public Periodos getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Periodos periodos) {
		this.periodos = periodos;
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

	public Calendar getDataAssinatura() {
		return dataAssinatura;
	}

	public void setDataAssinatura(Calendar dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
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

	public Boolean getIsFechado() {
		return isFechado;
	}

	public void setIsFechado(Boolean isFechado) {
		this.isFechado = isFechado;
	}

	public Boolean getIsExecutado() {
		return isExecutado;
	}

	public void setIsExecutado(Boolean isExecutado) {
		this.isExecutado = isExecutado;
	}

	public Boolean getIsAssinado() {
		return isAssinado;
	}

	public void setIsAssinado(Boolean isAssinado) {
		this.isAssinado = isAssinado;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}