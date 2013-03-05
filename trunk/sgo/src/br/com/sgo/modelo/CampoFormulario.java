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
@Table(name="CAMPOFORMULARIO")
public class CampoFormulario {

	@Id	
	@Column(name = "campo_id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long campo_id;
	
	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="formulariosJanela_id",updatable = true, nullable = false) 
	private FormulariosJanela formulariosJanela;

	@ManyToOne
	@JoinColumn(name="colunabd_id",updatable = true, nullable = false) 
	private ColunaBd colunabd;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;	

	@Column(name = "ismostrado")
	private Boolean ismostrado;

	@Column(name = "issomenteleitura")
	private Boolean issomenteleitura;

	@Column(name = "sequenciacampos")
	private Integer sequenciacampos;
	
	public Long getCampo_id() {
		return campo_id;
	}

	public void setCampo_id(Long campo_id) {
		this.campo_id = campo_id;
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

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSequenciacampos() {
		return sequenciacampos;
	}

	public void setSequenciacampos(Integer sequenciacampos) {
		this.sequenciacampos = sequenciacampos;
	}

	public Boolean getIsmostrado() {
		return ismostrado;
	}

	public void setIsmostrado(Boolean ismostrado) {
		this.ismostrado = ismostrado;
	}

	public Boolean getIssomenteleitura() {
		return issomenteleitura;
	}

	public void setIssomenteleitura(Boolean issomenteleitura) {
		this.issomenteleitura = issomenteleitura;
	}

}
