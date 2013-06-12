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
@Table(name = "PRODUTO")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "produto_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long produto_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "categoriaproduto_id", updatable = true, nullable = true)
	private CategoriaProduto categoriaProduto;
	
	@ManyToOne
	@JoinColumn(name = "grupoproduto_id", updatable = true, nullable = true)
	private GrupoProduto grupoProduto;
	
	@ManyToOne
	@JoinColumn(name = "subgrupoproduto_id", updatable = true, nullable = true)
	private SubGrupoProduto subGrupoProduto;
	
	@ManyToOne
	@JoinColumn(name = "localidade_id", updatable = true, nullable = true)
	private Localidade localidade;

	@ManyToOne
	@JoinColumn(name = "tipoproduto_id", updatable = true, nullable = true)
	private TipoProduto tipoProduto;

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

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "isestocado")
	private Boolean isEstocado;
	
	@Column(name = "isvendido")
	private Boolean isVendido;
	
	@Column(name = "iscomprado")
	private Boolean isComprado;
	
	@Column(name = "islistamateriais")
	private Boolean isListaMateriais;
	
	@Column(name = "isprodutocontrato")
	private Boolean isProdutoContrato;
	
	@Column(name = "isactive")
	private Boolean isActive;
	
	public Long getProduto_id() {
		return produto_id;
	}

	public void setProduto_id(Long produto_id) {
		this.produto_id = produto_id;
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

	public CategoriaProduto getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
	}

	public SubGrupoProduto getSubGrupoProduto() {
		return subGrupoProduto;
	}

	public void setSubGrupoProduto(SubGrupoProduto subGrupoProduto) {
		this.subGrupoProduto = subGrupoProduto;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
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

	public Boolean getIsEstocado() {
		return isEstocado;
	}

	public void setIsEstocado(Boolean isEstocado) {
		this.isEstocado = isEstocado;
	}

	public Boolean getIsVendido() {
		return isVendido;
	}

	public void setIsVendido(Boolean isVendido) {
		this.isVendido = isVendido;
	}

	public Boolean getIsComprado() {
		return isComprado;
	}

	public void setIsComprado(Boolean isComprado) {
		this.isComprado = isComprado;
	}

	public Boolean getIsListaMateriais() {
		return isListaMateriais;
	}

	public void setIsListaMateriais(Boolean isListaMateriais) {
		this.isListaMateriais = isListaMateriais;
	}

	public Boolean getIsProdutoContrato() {
		return isProdutoContrato;
	}

	public void setIsProdutoContrato(Boolean isProdutoContrato) {
		this.isProdutoContrato = isProdutoContrato;
	}

}
