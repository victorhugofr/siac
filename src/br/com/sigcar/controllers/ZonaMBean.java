package br.com.sigcar.controllers;

import br.com.sigcar.dominio.Endereco;
import br.com.sigcar.negocio.ZonaService;

public class ZonaMBean {

	private Endereco endereco;
	private ZonaService zonaService;
	
	public String obterZona() {
		zonaService.getZona(endereco.getCep());
		
		return "";
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
