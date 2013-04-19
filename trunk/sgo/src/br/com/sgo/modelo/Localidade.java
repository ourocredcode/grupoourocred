package br.com.sgo.modelo;

import java.io.Serializable;

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
@Table(name = "LOCALIDADE")
public class Localidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "localidade_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long localidade_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name = "pais_id", updatable = true, nullable = false)
	private Pais pais;

	@ManyToOne
	@JoinColumn(name = "cidade_id", updatable = true, nullable = false)
	private Cidade cidade;

	@ManyToOne
	@JoinColumn(name = "regiao_id", updatable = true, nullable = false)
	private Regiao regiao;

	@ManyToOne
	@JoinColumn(name = "tipolocalidade_id", updatable = true, nullable = false)
	private TipoLocalidade tipoLocalidade;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cep")
	private String cep;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "isactive")
	private Boolean isActive;

	public Long getLocalidade_id() {
		return localidade_id;
	}

	public void setLocalidade_id(Long localidade_id) {
		this.localidade_id = localidade_id;
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public TipoLocalidade getTipoLocalidade() {
		return tipoLocalidade;
	}

	public void setTipoLocalidade(TipoLocalidade tipoLocalidade) {
		this.tipoLocalidade = tipoLocalidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
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

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
}
