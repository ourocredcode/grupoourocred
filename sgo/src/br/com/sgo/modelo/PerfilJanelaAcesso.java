package br.com.sgo.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;

@Component
@Embeddable
@Table(name="PERFILJANELAACESSO")
public class PerfilJanelaAcesso {

	@ManyToOne
	@JoinColumn(name="empresa_id",updatable = true, nullable = false) 
	private Empresa empresa;
		
	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = true, nullable = false) 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name = "perfil_id",updatable = true, nullable = false)  
	private Perfil perfil;  

	@ManyToOne
	@JoinColumn(name = "janela_id")  
	private Janela janela;
	
	@Column(name="isactive")
	private Boolean isactive;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Janela getJanela() {
		return janela;
	}

	public void setJanela(Janela janela) {
		this.janela = janela;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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
