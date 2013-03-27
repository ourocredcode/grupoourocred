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
@Table(name="CONTABANCARIA")
public class ContaBancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "contabancaria_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long contaBancaria_id;
	
	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;
		
	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name="agencia_id",updatable = true, nullable = true) 
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(name="tipoconta_id",updatable = true, nullable = true) 
	private TipoConta tipoConta;
	
	@Column(name="numeroconta")
	private String numeroconta;

	@Column(name="isactive")
	private Boolean isActive;

	public Long getContaBancaria_id() {
		return contaBancaria_id;
	}

	public void setContaBancaria_id(Long contaBancaria_id) {
		this.contaBancaria_id = contaBancaria_id;
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

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumeroconta() {
		return numeroconta;
	}

	public void setNumeroconta(String numeroconta) {
		this.numeroconta = numeroconta;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
