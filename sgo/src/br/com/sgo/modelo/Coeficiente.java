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
@Table(name = "COEFICIENTE")
public class Coeficiente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "coeficiente_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long coeficiente_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name = "tabela_id", updatable = true, nullable = false)
	private Tabela tabela;
	
	@Column(name="created")
	private Calendar created;

	@Column(name="updated")
	private Calendar updated;

	@Column(name="valor")
	private Double valor;

	@Column(name="percentualmeta")
	private Double percentualMeta;

	@Column(name = "isactive")
	private Boolean isActive;
	
	public Long getCoeficiente_id() {
		return coeficiente_id;
	}

	public void setCoeficiente_id(Long coeficiente_id) {
		this.coeficiente_id = coeficiente_id;
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

	public Tabela getTabela() {
		return tabela;
	}

	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getPercentualMeta() {
		return percentualMeta;
	}

	public void setPercentualMeta(Double percentualMeta) {
		this.percentualMeta = percentualMeta;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
