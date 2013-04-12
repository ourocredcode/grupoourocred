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
@Table(name = "ELEMENTOBD")
public class ElementoBd {

	@Id
	@Column(name = "elementobd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long elementoBd_id;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "nomecolunabd")
	private String nomeColunaBd;

	public Long getElementoBd_id() {
		return elementoBd_id;
	}

	public void setElementoBd_id(Long elementoBd_id) {
		this.elementoBd_id = elementoBd_id;
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

	public String getNomeColunaBd() {
		return nomeColunaBd;
	}

	public void setNomeColunaBd(String nomeColunaBd) {
		this.nomeColunaBd = nomeColunaBd;
	}

}
