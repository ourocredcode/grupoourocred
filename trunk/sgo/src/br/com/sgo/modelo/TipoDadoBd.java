package br.com.sgo.modelo;

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
@Table(name="TIPODADOBD")
public class TipoDadoBd {

	@Id
	@Column(name = "tipodadobd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long tipoDadoBd_id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="chave")
	private String chave;

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;
	

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Long getTipoDadoBd_id() {
		return tipoDadoBd_id;
	}

	public void setTipoDadoBd_id(Long tipoDadoBd_id) {
		this.tipoDadoBd_id = tipoDadoBd_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
}
