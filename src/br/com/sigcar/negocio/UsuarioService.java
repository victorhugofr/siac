package br.com.sigcar.negocio;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.sigcar.dominio.Usuario;
import br.com.sigcar.exceptions.NegocioException;
import br.com.sigcar.repositorios.UsuarioRepositorio;

@Stateless
public class UsuarioService {

	@Inject
	private UsuarioRepositorio usuarioRepositorio;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario cadastrar(Usuario usuario) throws NegocioException {
		Usuario usuarioBd = usuarioRepositorio.getUsuario(usuario.getLogin());
		if(usuarioBd==null)
			usuarioRepositorio.salvar(usuario);
		else
			throw new NegocioException("Login ja cadastrado");
		return usuario;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String logar(Usuario usuarioLogado, Usuario usuario) throws NegocioException {
		Usuario usuarioBd = usuarioRepositorio.getUsuario(usuario.getLogin());
		if (usuarioBd != null && usuarioBd.getSenha().equals(usuario.getSenha())) {
			usuarioLogado = usuarioBd;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuarioLogado);
			return "/pages/index.jsf?faces-redirect=true";
		} else if(usuarioBd != null && !usuarioBd.getSenha().equals(usuario.getSenha())){
			throw new NegocioException("Senha incorreta");
		}else {
			throw new NegocioException("Usuario não encontrado");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Usuario usuario) {
		usuarioRepositorio.remover(usuario);
	}
	
	public List<Usuario> listar(){
		return usuarioRepositorio.listarUsuarios();
	}
}
