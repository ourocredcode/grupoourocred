package br.com.sgo.interceptor;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;


@Component
@SessionScoped
public class UsuarioInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Empresa empresa;
	private Organizacao organizacao;
	private String mail;

    public Usuario getUsuario() {
        return usuario;
    }

    public void login(Usuario usuario) {
        this.usuario = usuario;
        this.empresa = usuario.getEmpresa();
        this.organizacao = usuario.getOrganizacao();
    }

    public void logout() {
        this.usuario = null;
        this.empresa = null;
        this.organizacao = null;
    }

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Organizacao getOrganizacao() {
		return organizacao;
	}
}
