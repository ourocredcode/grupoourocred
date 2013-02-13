package br.com.sgo.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.caelum.vraptor.ioc.Component;

@Component
@Embeddable
@Table(name="USUARIOPERFIL")
public class UsuarioPerfil {
	
	@ManyToOne
	@JoinColumn(name="empresa_id") 
	private Empresa empresa;
		
	@ManyToOne
	@JoinColumn(name="organizacao_id",updatable = false, insertable=false) 
	private Organizacao organizacao;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id",updatable = false, insertable=false)  
	private Usuario usuario;  
	
	@ManyToOne
	@JoinColumn(name = "perfil_id",updatable = false, insertable=false)  
	private Perfil perfil;  
	
	@Column(name="isactive")
	private Boolean isactive;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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
