package br.com.sigcar.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sigcar.dominio.Funcionario;
import br.com.sigcar.repositorios.FuncionarioRepositorio;

@Named("funcionarioMBean")
@SessionScoped
public class FuncionarioMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private FuncionarioRepositorio funcionarioRepositorio;
	private Funcionario funcionario;
	private Funcionario funcionarioLogado;
	private DataModel<Funcionario> funcionariosModel;

	public DataModel<Funcionario> getUsuariosModel() {
		return funcionariosModel;
	}

	public void setUsuariosModel(DataModel<Funcionario> funcionariosModel) {
		this.funcionariosModel = funcionariosModel;
	}

	public String logar() {
		Funcionario funcionarioBd = funcionarioRepositorio.getFuncionario(funcionario.getLogin());
		if (funcionarioBd != null && funcionarioBd.getSenha().equals(funcionario.getSenha())) {
			funcionarioLogado = funcionarioBd;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("funcionarioLogado", funcionarioLogado);
			return "/pages/index.jsf?faces-redirect=true";
		} else if(funcionarioBd != null && !funcionarioBd.getSenha().equals(funcionario.getSenha())){
			FacesMessage msg = new FacesMessage("Senha incorreta","");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}else {
			FacesMessage msg = new FacesMessage("Funcionario não encontrado","");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();

		if (funcionarioLogado != null) {
			funcionarioLogado = null;
			funcionario = null;
		}
		try {
			context.getExternalContext().redirect("/sistemacartorial/login/login.jsf?faces-redirect=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/sistemacartorial/login/login.jsf?faces-redirect=true";

	}

	public String listarFuncionarios() {
		funcionariosModel = new ListDataModel<Funcionario>(funcionarioRepositorio.listarUsuarios());

		return "/pages/funcionarios/list.jsf?faces-redirect=true";
	}
	
	public String removerFuncionario() {
		Funcionario usuarioRemovido = funcionariosModel.getRowData();
		funcionarioRepositorio.remover(usuarioRemovido);
		//return listarMateriais();
		funcionariosModel = new ListDataModel<Funcionario>(funcionarioRepositorio.listarUsuarios());
		return "/pages/funcionarios/list.jsf";
	}

	public String cadastrar() {
		Date dataCadastro = new Date();
		funcionario.setDataCadastro(dataCadastro);
		funcionarioRepositorio.salvar(funcionario);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("funcionarioLogado", funcionario);
		return "/pages/index.jsf";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario usuario) {
		this.funcionario = usuario;
	}

	public Funcionario getFuncionarioLogado() {
		return funcionarioLogado;
	}

	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}

	public FuncionarioMBean() {
		funcionario = new Funcionario();
	}
}
