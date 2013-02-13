package br.com.sgo.modelo;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UsuarioOrgAcesso {

	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name="organizacao_id") 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")  
	private Usuario usuario;  

	@Column(name="isactive")
	private Boolean isactive;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

}
