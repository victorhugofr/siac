package br.com.sigcar.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Funcionario extends Usuario{

	
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;
	private String funcao;
	
	public Funcionario() {
	}

	public Funcionario(String login, String senha) {
		super(login,senha);
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
	public void setDataAdmissao(String dataAdmissao) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd"); 
		Date data = formato.parse(dataAdmissao);
		this.dataAdmissao = data;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
}
