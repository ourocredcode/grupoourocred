package br.com.sgo.modelo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Entity
@Component
@SessionScoped
@Table(name="PERFIL")
public class Perfil {

	@Id
	@Column(name = "perfil_id")  
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long perfil_id;

	@Column(name="nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private Empresa empresa;
		
	@ManyToOne
	@JoinColumn(name="organizacao_id") 
	private Organizacao organizacao;

	@OneToMany
	@JoinTable(name = "PERFILORGACESSO", 
	joinColumns = { @JoinColumn(name = "perfil_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "organizacao_id") }) 
	private Collection<Organizacao> organizacoes;

	public Long getPerfil_id() {
		return perfil_id;
	}

	public void setPerfil_id(Long perfil_id) {
		this.perfil_id = perfil_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Organizacao> getOrganizacoes() {
		return organizacoes;
	}

	public void setOrganizacoes(Collection<Organizacao> organizacoes) {
		this.organizacoes = organizacoes;
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
}
