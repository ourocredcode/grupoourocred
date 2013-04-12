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
@Table(name = "CAMPOFORMULARIO")
public class CampoFormulario {

	@Id
	@Column(name = "campo_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long campo_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "formulariosJanela_id", updatable = true, nullable = false)
	private FormulariosJanela formulariosJanela;

	@ManyToOne
	@JoinColumn(name = "colunabd_id", updatable = true, nullable = false)
	private ColunaBd colunaBd;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "ismostrado")
	private Boolean isMostrado;

	@Column(name = "issomenteleitura")
	private Boolean isSomenteLeitura;

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

	public ColunaBd getColunaBd() {
		return colunaBd;
	}

	public void setColunaBd(ColunaBd colunaBd) {
		this.colunaBd = colunaBd;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsMostrado() {
		return isMostrado;
	}

	public void setIsMostrado(Boolean isMostrado) {
		this.isMostrado = isMostrado;
	}

	public Boolean getIsSomenteLeitura() {
		return isSomenteLeitura;
	}

	public void setIsSomenteLeitura(Boolean isSomenteLeitura) {
		this.isSomenteLeitura = isSomenteLeitura;
	}
}
