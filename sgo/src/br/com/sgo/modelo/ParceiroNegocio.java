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
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name = "PARCEIRONEGOCIO")
public class ParceiroNegocio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parceironegocio_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long parceiroNegocio_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "classificacaoparceiro_id", updatable = true, nullable = true)
	private ClassificacaoParceiro classificacaoParceiro;

	@ManyToOne
	@JoinColumn(name = "tipoparceiro_id", updatable = true, nullable = true)
	private TipoParceiro tipoParceiro;

	@ManyToOne
	@JoinColumn(name = "grupoparceiro_id", updatable = true, nullable = true)
	private GrupoParceiro grupoParceiro;

	@ManyToOne
	@JoinColumn(name = "categoriaparceiro_id", updatable = true, nullable = true)
	private CategoriaParceiro categoriaParceiro;

	@ManyToOne
	@JoinColumn(name = "estadocivil_id", updatable = true, nullable = true)
	private EstadoCivil estadoCivil;

	@ManyToOne
	@JoinColumn(name = "sexo_id", updatable = true, nullable = true)
	private Sexo sexo;

	@Column(name = "pn_id")
	private Long pn_id;

	@ManyToOne
	@JoinColumn(name = "idioma_id", updatable = true, nullable = true)
	private Idioma idioma;

	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = false)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = false)
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

	@Column(name = "nomefantasia")
	private String nomeFantasia;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "ie")
	private String ie;

	@Column(name = "rg")
	private String rg;

	@Column(name = "ccm")
	private String ccm;

	@Column(name = "datanascimento")
	private Calendar dataNascimento;

	@Column(name = "iscliente")
	private Boolean isCliente;

	@Column(name = "isfornecedor")
	private Boolean isFornecedor;

	@Column(name = "isfuncionario")
	private Boolean isFuncionario;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getParceiroNegocio_id() {
		return parceiroNegocio_id;
	}

	public void setParceiroNegocio_id(Long parceiroNegocio_id) {
		this.parceiroNegocio_id = parceiroNegocio_id;
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

	public ClassificacaoParceiro getClassificacaoParceiro() {
		return classificacaoParceiro;
	}

	public void setClassificacaoParceiro(
			ClassificacaoParceiro classificacaoParceiro) {
		this.classificacaoParceiro = classificacaoParceiro;
	}

	public TipoParceiro getTipoParceiro() {
		return tipoParceiro;
	}

	public void setTipoParceiro(TipoParceiro tipoParceiro) {
		this.tipoParceiro = tipoParceiro;
	}

	public GrupoParceiro getGrupoParceiro() {
		return grupoParceiro;
	}

	public void setGrupoParceiro(GrupoParceiro grupoParceiro) {
		this.grupoParceiro = grupoParceiro;
	}

	public CategoriaParceiro getCategoriaParceiro() {
		return categoriaParceiro;
	}

	public void setCategoriaParceiro(CategoriaParceiro categoriaParceiro) {
		this.categoriaParceiro = categoriaParceiro;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
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

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCcm() {
		return ccm;
	}

	public void setCcm(String ccm) {
		this.ccm = ccm;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Boolean getIsCliente() {
		return isCliente == null ? false : true;
	}

	public void setIsCliente(Boolean isCliente) {
		this.isCliente = isCliente;
	}

	public Boolean getIsFornecedor() {
		return isFornecedor == null ? false : true;
	}

	public void setIsFornecedor(Boolean isFornecedor) {
		this.isFornecedor = isFornecedor;
	}

	public Boolean getIsFuncionario() {
		return isFuncionario == null ? false : true;
	}

	public void setIsFuncionario(Boolean isFuncionario) {
		this.isFuncionario = isFuncionario;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getPn_id() {
		return pn_id;
	}

	public void setPn_id(Long pn_id) {
		this.pn_id = pn_id;
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
}
