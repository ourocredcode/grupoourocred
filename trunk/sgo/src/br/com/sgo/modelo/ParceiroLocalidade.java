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
@Table(name="PARCEIROLOCALIDADE")
public class ParceiroLocalidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parceirolocalidade_id")  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long parceiroLocalidade_id;

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="parceironegocio_id",updatable = true, nullable = true) 
	private ParceiroNegocio parceiroNegocio; 

	@ManyToOne
	@JoinColumn(name="localidade_id",updatable = true, nullable = true) 
	private Localidade localidade;

	@Column(name="nome")
	private String nome;

	@Column(name="descricao")
	private String descricao;

	@Column(name="isentrega")
	private Boolean isEntrega;

	@Column(name="iscobranca")
	private Boolean isCobranca;
	
	@Column(name="isfatura")
	private Boolean isFatura;
	
	@Column(name="isassinatura")
	private Boolean isAssinatura;
	
	@Column(name="isresidencial")
	private Boolean isResidencial;

	@Column(name="isactive")
	private Boolean isActive;
	
	@Column(name="numero")
	private String numero;
	
	@Column(name="complemento")
	private String complemento;

	public Long getParceiroLocalidade_id() {
		return parceiroLocalidade_id;
	}

	public void setParceiroLocalidade_id(Long parceiroLocalidade_id) {
		this.parceiroLocalidade_id = parceiroLocalidade_id;
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

	public ParceiroNegocio getParceiroNegocio() {
		return parceiroNegocio;
	}

	public void setParceiroNegocio(ParceiroNegocio parceiroNegocio) {
		this.parceiroNegocio = parceiroNegocio;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
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

	public Boolean getIsEntrega() {
		return isEntrega;
	}

	public void setIsEntrega(Boolean isEntrega) {
		this.isEntrega = isEntrega;
	}

	public Boolean getIsCobranca() {
		return isCobranca;
	}

	public void setIsCobranca(Boolean isCobranca) {
		this.isCobranca = isCobranca;
	}

	public Boolean getIsFatura() {
		return isFatura;
	}

	public void setIsFatura(Boolean isFatura) {
		this.isFatura = isFatura;
	}

	public Boolean getIsAssinatura() {
		return isAssinatura;
	}

	public void setIsAssinatura(Boolean isAssinatura) {
		this.isAssinatura = isAssinatura;
	}

	public Boolean getIsResidencial() {
		return isResidencial;
	}

	public void setIsResidencial(Boolean isResidencial) {
		this.isResidencial = isResidencial;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
