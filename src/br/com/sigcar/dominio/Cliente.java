package br.com.sigcar.dominio;

import javax.persistence.Entity;

@Entity
public class Cliente extends Usuario{

	public Cliente() {
		
	}
	public Cliente(String login, String senha) {
		super(login,senha);
	}
}
