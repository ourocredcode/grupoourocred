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
@Table(name="PARCEIROBENEFICIO")
public class ParceiroBeneficio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parceirobeneficio_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long parceiroBeneficio_id;
	
	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name="parceironegocio_id",updatable = true, nullable = false) 
	private ParceiroNegocio parceiroNegocio;

	@Column(name="nome")
	private String nome;
	
	@Column(name="descricao")
	private String descricao;

	@Column(name="numerobeneficio")
	private String numeroBeneficio;
	
	@Column(name="isactive")
	private Boolean isActive;

	public Long getParceiroBeneficio_id() {
		return parceiroBeneficio_id;
	}

	public void setParceiroBeneficio_id(Long parceiroBeneficio_id) {
		this.parceiroBeneficio_id = parceiroBeneficio_id;
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

	public String getNumeroBeneficio() {
		return numeroBeneficio;
	}

	public void setNumeroBeneficio(String numeroBeneficio) {
		this.numeroBeneficio = numeroBeneficio;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
