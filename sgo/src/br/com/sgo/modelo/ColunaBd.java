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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;

@Entity
@Component
@Table(name = "COLUNABD")
public class ColunaBd implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "colunabd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long colunabd_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "tabelabd_id", updatable = true, nullable = false)
	private TabelaBd tabelaBd;

	@OneToOne
	@JoinColumn(name = "tipodadobd_id", updatable = true, nullable = false)
	private TipoDadoBd tipoDadoBd;

	@ManyToOne
	@JoinColumn(name = "elementobd_id", updatable = true, nullable = false)
	private ElementoBd elementoBd;

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

	@Column(name = "nomecolunabd")
	private String nomeColunaBd;

	public String getNomeColunaBd() {
		return nomeColunaBd;
	}

	public void setNomeColunaBd(String nomeColunaBd) {
		this.nomeColunaBd = nomeColunaBd;
	}

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

	public ElementoBd getElementoBd() {
		return elementoBd;
	}

	public void setElementoBd(ElementoBd elementoBd) {
		this.elementoBd = elementoBd;
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
