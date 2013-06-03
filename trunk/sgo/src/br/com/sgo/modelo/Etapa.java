package br.com.sgo.modelo;

import java.io.Serializable;
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
@Table(name = "ETAPA")
public class Etapa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "etapa_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long etapa_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = false)
	private Usuario updatedBy;

	@Column(name = "ordemetapa")
	private Integer ordemEtapa;

	@Column(name = "ispadrao")
	private Boolean isPadrao;

	@Column(name = "created")
	private Calendar created;

	@Column(name = "updated")
	private Calendar updated;

	@Column(name = "chave")
	private String chave;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getEtapa_id() {
		return etapa_id;
	}

	public void setEtapa_id(Long etapa_id) {
		this.etapa_id = etapa_id;
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

	public Integer getOrdemEtapa() {
		return ordemEtapa;
	}

	public void setOrdemEtapa(Integer ordemEtapa) {
		this.ordemEtapa = ordemEtapa;
	}

	public Boolean getIsPadrao() {
		return isPadrao;
	}

	public void setIsPadrao(Boolean isPadrao) {
		this.isPadrao = isPadrao;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}