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
@Table(name="TABELACOEFICIENTE")
public class TabelaCoeficiente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "tabelacoeficiente_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long tabelaCoeficiente_id;
	
	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;

	@ManyToOne
	@JoinColumn(name="tabela_id",updatable = true, nullable = false) 
	private Tabela tabela;
	
	@Column(name="nome")
	private String nome;

	@Column(name="descricao")
	private String descricao;

	@Column(name="isactive")
	private Boolean isActive;

	public Long getTabelaCoeficiente_id() {
		return tabelaCoeficiente_id;
	}

	public void setTabelaCoeficiente_id(Long tabelaCoeficiente_id) {
		this.tabelaCoeficiente_id = tabelaCoeficiente_id;
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
