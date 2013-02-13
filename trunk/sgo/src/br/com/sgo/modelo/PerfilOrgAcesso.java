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
public class PerfilOrgAcesso {

	@ManyToOne
	@JoinColumn(name = "perfil_id")  
	private Perfil perfil; 

	@ManyToOne
	@JoinColumn(name = "organizacao_id")  
	private Organizacao organizacao; 
	
	@Column(name="isactive")
	private Boolean isactive;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

}
