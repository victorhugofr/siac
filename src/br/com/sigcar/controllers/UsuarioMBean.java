package br.com.sigcar.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sigcar.dominio.Usuario;
import br.com.sigcar.exceptions.NegocioException;
import br.com.sigcar.negocio.UsuarioService;
import br.com.sigcar.repositorios.UsuarioRepositorio;

@Named("usuarioMBean")
@SessionScoped
public class UsuarioMBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Usuario usuarioLogado;
	private DataModel<Usuario> usuariosModel;
	
	@EJB
	private UsuarioService usuarioService;

	public DataModel<Usuario> getUsuariosModel() {
		return usuariosModel;
	}

	public void setUsuariosModel(DataModel<Usuario> usuariosModel) {
		this.usuariosModel = usuariosModel;
	}

	public String logar() {
		
		try {
			return usuarioService.logar(usuarioLogado, usuario);
		} catch (NegocioException e) {
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();

		if (usuarioLogado != null) {
			usuarioLogado = null;
			usuario = null;
		}
		try {
			context.getExternalContext().redirect("/sistemacartorial/login/login.jsf?faces-redirect=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/sistemacartorial/login/login.jsf?faces-redirect=true";

	}

	public String listarUsuarios() {
		usuariosModel = new ListDataModel<Usuario>(usuarioService.listar());

		return "/pages/usuarios/list.jsf?faces-redirect=true";
	}
	
	public String removerUsuario() {
		Usuario usuarioRemovido = usuariosModel.getRowData();
		usuarioService.remover(usuarioRemovido);
		usuariosModel = new ListDataModel<Usuario>(usuarioService.listar());
		return "/pages/usuarios/list.jsf";
	}

	public String cadastrar() {
		Date dataCadastro = new Date();
		usuario.setDataCadastro(dataCadastro);
		try {
			usuarioService.cadastrar(usuario);
		} catch (NegocioException e) {
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().
			addMessage("", msg);
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
		return "/pages/index.jsf";
	}

	public String cadastrarLogado() {
		Date dataCadastro = new Date();
		usuario.setDataCadastro(dataCadastro);
		try {
			usuarioService.cadastrar(usuario);
		} catch (NegocioException e) {
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().
			addMessage("", msg);
		}
		return "/pages/index.jsf";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioMBean() {
		usuario = new Usuario();
	}
}
