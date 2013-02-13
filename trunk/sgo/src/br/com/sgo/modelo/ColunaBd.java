package br.com.sgo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name="COLUNABD")
public class ColunaBd {

	@Id	
	@Column(name = "colunabd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long colunabd_id;

	@Column(name="nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = false, insertable=false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = false, insertable=false) 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="tabelabd_id",updatable = false, insertable=false) 
	private TabelaBd tabelaBd;

	@OneToOne
	@JoinColumn(name="tipodadobd_id",updatable = false, insertable=false)
	private TipoDadoBd tipoDadoBd;

	public Long getColunabd_id() {
		return colunabd_id;
	}

	public void setColunabd_id(Long colunabd_id) {
		this.colunabd_id = colunabd_id;
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

	public TabelaBd getTabelaBd() {
		return tabelaBd;
	}

	public void setTabelaBd(TabelaBd tabelaBd) {
		this.tabelaBd = tabelaBd;
	}

	public TipoDadoBd getTipoDadoBd() {
		return tipoDadoBd;
	}

	public void setTipoDadoBd(TipoDadoBd tipoDadoBd) {
		this.tipoDadoBd = tipoDadoBd;
	}
}
