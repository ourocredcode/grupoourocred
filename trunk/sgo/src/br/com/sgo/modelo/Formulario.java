package br.com.sgo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name = "FORMULARIO")
public class Formulario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "formulario_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long formulario_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "parceironegocio_id", updatable = true, nullable = false)
	private ParceiroNegocio parceiroNegocio;
	
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

	@Column(name = "isactive")
	private Boolean isActive;

	@Transient
	private Collection<Contrato> contratos = new ArrayList<Contrato>();

	@Transient
	private ParceiroLocalidade parceiroLocalidade;
	
	@Transient
	private ParceiroInfoBanco parceiroInfoBanco;
	
	@Transient
	private ParceiroBeneficio parceiroBeneficio;

	@Transient
	private Collection<ParceiroContato> parceiroContatos = new ArrayList<ParceiroContato>();
	
	@Transient
	private ControleFormulario posvenda;

	public Long getFormulario_id() {
		return formulario_id;
	}

	public void setFormulario_id(Long formulario_id) {
		this.formulario_id = formulario_id;
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

	public ParceiroNegocio getParceiroNegocio() {
		return parceiroNegocio;
	}

	public void setParceiroNegocio(ParceiroNegocio parceiroNegocio) {
		this.parceiroNegocio = parceiroNegocio;
	}
	
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Collection<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(Collection<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	public void adicionaContrato(Contrato contrato) {
		this.contratos.add(contrato);
	}

	public ParceiroLocalidade getParceiroLocalidade() {
		return parceiroLocalidade;
	}

	public void setParceiroLocalidade(ParceiroLocalidade parceiroLocalidade) {
		this.parceiroLocalidade = parceiroLocalidade;
	}

	public ParceiroInfoBanco getParceiroInfoBanco() {
		return parceiroInfoBanco;
	}

	public void setParceiroInfoBanco(ParceiroInfoBanco parceiroInfoBanco) {
		this.parceiroInfoBanco = parceiroInfoBanco;
	}

	public Collection<ParceiroContato> getParceiroContatos() {
		return parceiroContatos;
	}

	public void setParceiroContatos(Collection<ParceiroContato> parceiroContatos) {
		this.parceiroContatos = parceiroContatos;
	}

	public ParceiroBeneficio getParceiroBeneficio() {
		return parceiroBeneficio;
	}

	public void setParceiroBeneficio(ParceiroBeneficio parceiroBeneficio) {
		this.parceiroBeneficio = parceiroBeneficio;
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

	public ControleFormulario getPosvenda() {
		return posvenda;
	}

	public void setPosvenda(ControleFormulario posvenda) {
		this.posvenda = posvenda;
	}
}
