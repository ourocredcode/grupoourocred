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
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name="FORMULARIOSJANELA")
public class FormulariosJanela {

	@Id
	@Column(name = "formulariosjanela_id")  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long formulariosjanela_id;

	@Column(name="nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private Empresa empresa;
		
	@ManyToOne
	@JoinColumn(name="organizacao_id") 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name="janela_id") 
	private Janela janela;
	
	@ManyToOne
	@JoinColumn(name="tabelabd_id") 
	private TabelaBd tabelabd;
	
	@ManyToOne
	@JoinColumn(name="tabelabd_id") 
	private ColunaBd colunabd;

	public Long getFormulariosjanela_id() {
		return formulariosjanela_id;
	}

	public void setFormulariosjanela_id(Long formulariosjanela_id) {
		this.formulariosjanela_id = formulariosjanela_id;
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

	public Janela getJanela() {
		return janela;
	}

	public void setJanela(Janela janela) {
		this.janela = janela;
	}

	public TabelaBd getTabelabd() {
		return tabelabd;
	}

	public void setTabelabd(TabelaBd tabelabd) {
		this.tabelabd = tabelabd;
	}

	public ColunaBd getColunabd() {
		return colunabd;
	}

	public void setColunabd(ColunaBd colunabd) {
		this.colunabd = colunabd;
	}
}
