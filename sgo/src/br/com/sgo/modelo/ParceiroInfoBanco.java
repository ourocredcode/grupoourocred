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
@Table(name="PARCEIROINFOBANCO")
public class ParceiroInfoBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parceiroinfobanco_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long parceiroInfoBanco_id;

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
	@JoinColumn(name="banco_id",updatable = true, nullable = true) 
	private Banco banco;

	@ManyToOne
	@JoinColumn(name="agencia_id",updatable = true, nullable = true) 
	private Agencia agencia;

	@ManyToOne
	@JoinColumn(name="contabancaria_id",updatable = true, nullable = true) 
	private ContaBancaria contaBancaria;	
	
	@ManyToOne
	@JoinColumn(name="meiopagamento_id",updatable = true, nullable = false) 
	private MeioPagamento meioPagamento;

	@Column(name="isactive")
	private Boolean isActive;

	public Long getParceiroInfoBanco_id() {
		return parceiroInfoBanco_id;
	}

	public void setParceiroInfoBanco_id(Long parceiroInfoBanco_id) {
		this.parceiroInfoBanco_id = parceiroInfoBanco_id;
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

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}

	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public MeioPagamento getMeioPagamento() {
		return meioPagamento;
	}

	public void setMeioPagamento(MeioPagamento meioPagamento) {
		this.meioPagamento = meioPagamento;
	}

}
