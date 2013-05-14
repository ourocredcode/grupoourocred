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
@Table(name = "ORGANIZACAOINFO")
public class OrganizacaoInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "organizacao_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long organizacao_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "tipoorganizacao_id", updatable = true, nullable = false)
	private TipoOrganizacao tipoOrganizacao;

	@ManyToOne
	@JoinColumn(name = "localidade_id", updatable = true, nullable = false)
	private Localidade localidade;

	@ManyToOne
	@JoinColumn(name = "calendario_id", updatable = true, nullable = false)
	private Calendario calendario;

	@ManyToOne
	@JoinColumn(name = "pai_org_id", updatable = true, nullable = false)
	private Organizacao paiOrg;

	@ManyToOne
	@JoinColumn(name = "supervisor_user_id", updatable = true, nullable = false)
	private Usuario supervisorUser;

	@ManyToOne
	@JoinColumn(name = "logo_imagem_id", updatable = true, nullable = false)
	private Imagem logoImagem;
	
	@ManyToOne
	@JoinColumn(name = "createdby", updatable = true, nullable = true)
	private Usuario createdBy;

	@ManyToOne
	@JoinColumn(name = "updatedby", updatable = true, nullable = true)
	private Usuario updatedBy;

	@Column(name = "telefone1")
	private String telefone1;

	@Column(name = "telefone2")
	private String telefone2;

	@Column(name = "fax")
	private String fax;

	@Column(name = "email")
	private String email;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "ie")
	private String ie;

	@Column(name = "contato")
	private String contato;

	@Column(name = "nomefantasia")
	private String nomeFantasia;

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

	public Long getOrganizacao_id() {
		return organizacao_id;
	}

	public void setOrganizacao_id(Long organizacao_id) {
		this.organizacao_id = organizacao_id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoOrganizacao getTipoOrganizacao() {
		return tipoOrganizacao;
	}

	public void setTipoOrganizacao(TipoOrganizacao tipoOrganizacao) {
		this.tipoOrganizacao = tipoOrganizacao;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	public Organizacao getPaiOrg() {
		return paiOrg;
	}

	public void setPaiOrg(Organizacao paiOrg) {
		this.paiOrg = paiOrg;
	}

	public Usuario getSupervisorUser() {
		return supervisorUser;
	}

	public void setSupervisorUser(Usuario supervisorUser) {
		this.supervisorUser = supervisorUser;
	}

	public Imagem getLogoImagem() {
		return logoImagem;
	}

	public void setLogoImagem(Imagem logoImagem) {
		this.logoImagem = logoImagem;
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

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
