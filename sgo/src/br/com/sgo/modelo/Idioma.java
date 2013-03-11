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
@Table(name="IDIOMA")
public class Idioma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idioma_id")  
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long idioma_id;
	
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
	
	@Column(name="codigoisoidioma")
	private String codigoIsoIdioma;
	
	public Long getIdioma_id() {
		return idioma_id;
	}

	public void setIdioma_id(Long idioma_id) {
		this.idioma_id = idioma_id;
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

	public String getCodigoIsoIdioma() {
		return codigoIsoIdioma;
	}

	public void setCodigoIsoIdioma(String codigoIsoIdioma) {
		this.codigoIsoIdioma = codigoIsoIdioma;
	}

	public String getSiglaPais() {
		return siglaPais;
	}

	public void setSiglaPais(String siglaPais) {
		this.siglaPais = siglaPais;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name="siglapais")
	private String siglaPais;
	
	@Column(name="isactive")
	private Boolean isActive;

}
