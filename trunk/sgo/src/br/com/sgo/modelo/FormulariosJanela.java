package br.com.sgo.modelo;

import java.util.Calendar;

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
@Table(name = "FORMULARIOSJANELA")
public class FormulariosJanela {

	@Id
	@Column(name = "formulariosjanela_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long formulariosjanela_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "janela_id", updatable = true, nullable = false)
	private Janela janela;

	@ManyToOne
	@JoinColumn(name = "tabelabd_id", updatable = true, nullable = false)
	private TabelaBd tabelaBd;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "chave")
	private String chave;
	
	@Column(name = "value")
	private String value;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "isactive")
	private Boolean isActive;

	@Column(name = "ismostrado")
	private Boolean isMostrado;

	@Column(name = "issomenteleitura")
	private Boolean isSomenteLeitura;
	
	

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

	public TabelaBd getTabelaBd() {
		return tabelaBd;
	}

	public void setTabelaBd(TabelaBd tabelaBd) {
		this.tabelaBd = tabelaBd;
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

	public Usuario getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Usuario createdBy) {
		this.createdBy = createdBy;
	}

	public Usuario getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Usuario updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Calendar getUpdated() {
		return updated;
	}

	public void setUpdated(Calendar updated) {
		this.updated = updated;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
