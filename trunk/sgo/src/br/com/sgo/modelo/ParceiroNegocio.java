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
@Table(name="PARCEIRONEGOCIO")
public class ParceiroNegocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parceironegocio_id")  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long parceiroNegocio;

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="classificacaoparceiro_id",updatable = true, nullable = false) 
	private Organizacao classificacaoParceiro; 

	@ManyToOne
	@JoinColumn(name="tipoparceiro_id",updatable = true, nullable = false) 
	private Empresa tipoParceiro;

	@ManyToOne
	@JoinColumn(name="grupoparceiro_id",updatable = true, nullable = false) 
	private Organizacao grupoParceiro;

	@ManyToOne
	@JoinColumn(name="categoriaparceiro_id",updatable = true, nullable = false) 
	private Empresa categoriaParceiro;

	@ManyToOne
	@JoinColumn(name="banco_id",updatable = true, nullable = false) 
	private Organizacao banco;
	
	@ManyToOne
	@JoinColumn(name="idioma_id",updatable = true, nullable = false) 
	private Organizacao idioma;
	
	@Column(name="nome")
	private String nome;

	@Column(name="descricao")
	private String descricao;

	@Column(name="nomefantasia")
	private String nomeFantasia;

	@Column(name="cpf")
	private String cpf;
	
	@Column(name="cnpj")
	private String cnpj;
	
	@Column(name="ie")
	private String ie;

	@Column(name="rg")
	private String rg;
	
	@Column(name="ccm")
	private String ccm;
	
	@Column(name="iscliente")
	private Boolean isCliente;
	
	@Column(name="isfornecedor")
	private Boolean isFornecedor;
	
	@Column(name="isfuncionario")
	private Boolean isFuncionario;

	@Column(name="isisentodeimposto")
	private Boolean isIsentoImposto;

	@Column(name="isactive")
	private Boolean isActive;

	public Long getParceiroNegocio() {
		return parceiroNegocio;
	}

	public void setParceiroNegocio(Long parceiroNegocio) {
		this.parceiroNegocio = parceiroNegocio;
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

	public Organizacao getClassificacaoParceiro() {
		return classificacaoParceiro;
	}

	public void setClassificacaoParceiro(Organizacao classificacaoParceiro) {
		this.classificacaoParceiro = classificacaoParceiro;
	}

	public Empresa getTipoParceiro() {
		return tipoParceiro;
	}

	public void setTipoParceiro(Empresa tipoParceiro) {
		this.tipoParceiro = tipoParceiro;
	}

	public Organizacao getGrupoParceiro() {
		return grupoParceiro;
	}

	public void setGrupoParceiro(Organizacao grupoParceiro) {
		this.grupoParceiro = grupoParceiro;
	}

	public Empresa getCategoriaParceiro() {
		return categoriaParceiro;
	}

	public void setCategoriaParceiro(Empresa categoriaParceiro) {
		this.categoriaParceiro = categoriaParceiro;
	}

	public Organizacao getIdioma() {
		return idioma;
	}

	public void setIdioma(Organizacao idioma) {
		this.idioma = idioma;
	}

	public Organizacao getBanco() {
		return banco;
	}

	public void setBanco(Organizacao banco) {
		this.banco = banco;
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

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCcm() {
		return ccm;
	}

	public void setCcm(String ccm) {
		this.ccm = ccm;
	}

	public Boolean getIsCliente() {
		return isCliente;
	}

	public void setIsCliente(Boolean isCliente) {
		this.isCliente = isCliente;
	}

	public Boolean getIsFornecedor() {
		return isFornecedor;
	}

	public void setIsFornecedor(Boolean isFornecedor) {
		this.isFornecedor = isFornecedor;
	}

	public Boolean getIsFuncionario() {
		return isFuncionario;
	}

	public void setIsFuncionario(Boolean isFuncionario) {
		this.isFuncionario = isFuncionario;
	}

	public Boolean getIsIsentoImposto() {
		return isIsentoImposto;
	}

	public void setIsIsentoImposto(Boolean isIsentoImposto) {
		this.isIsentoImposto = isIsentoImposto;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
}
