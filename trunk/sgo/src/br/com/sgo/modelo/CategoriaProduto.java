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
@Table(name="CATEGORIAPRODUTO")
public class CategoriaProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "categoriaproduto_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long categoriaProduto_id;

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;

	@Column(name="nome")
	private String nome;

	@Column(name="descricao")
	private String descricao;

	@Column(name="isactive")
	private Boolean isActive;

	public Long getCategoriaProduto_id() {
		return categoriaProduto_id;
	}

	public void setCategoriaProduto_id(Long categoriaProduto_id) {
		this.categoriaProduto_id = categoriaProduto_id;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}