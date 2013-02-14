package br.com.sgo.interceptor;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.sgo.modelo.Usuario;


@Component
@SessionScoped
public class UsuarioInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private String mail;

    public Usuario getUsuario() {
        return usuario;
    }

    public void login(Usuario usuario) {
        this.usuario = usuario;
    }

    public void logout() {
        this.usuario = null;
    }

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
