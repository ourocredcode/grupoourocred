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
@Table(name="CAMPOFORMULARIO")
public class CampoFormulario {

	@Id	
	@Column(name = "campo_id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long campo_id;

	@Column(name = "nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id") 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private FormulariosJanela formulariosJanela;

	@ManyToOne
	@JoinColumn(name="colunabd_id") 
	private ColunaBd colunabd;

	public Long getCampo_id() {
		return campo_id;
	}

	public void setCampo_id(Long campo_id) {
		this.campo_id = campo_id;
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

	public FormulariosJanela getFormulariosJanela() {
		return formulariosJanela;
	}

	public void setFormulariosJanela(FormulariosJanela formulariosJanela) {
		this.formulariosJanela = formulariosJanela;
	}

	public ColunaBd getColunabd() {
		return colunabd;
	}

	public void setColunabd(ColunaBd colunabd) {
		this.colunabd = colunabd;
	}
}
