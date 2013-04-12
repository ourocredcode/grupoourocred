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

@Entity
@Component
@Table(name = "TABELABD")
public class TabelaBd implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tabelabd_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tabelaBd_id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "nomeTabelaBd")
	private String nomeTabelaBd;

	@ManyToOne
	@JoinColumn(name = "empresa_id", updatable = true, nullable = false)
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "organizacao_id", updatable = true, nullable = false)
	private Organizacao organizacao;

	public Long getTabelaBd_id() {
		return tabelaBd_id;
	}

	public void setTabelaBd_id(Long tabelaBd_id) {
		this.tabelaBd_id = tabelaBd_id;
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

	public String getNomeTabelaBd() {
		return nomeTabelaBd;
	}

	public void setNomeTabelaBd(String nomeTabelaBd) {
		this.nomeTabelaBd = nomeTabelaBd;
	}
}
