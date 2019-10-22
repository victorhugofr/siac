package br.com.sigcar.negocio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.google.gson.Gson;

import br.com.sigcar.dominio.Endereco;

@Named("zonaService")
@SessionScoped
public class ZonaService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cep;
	private String cartorio = "";
	
	public ZonaService(){
		
	}
	public static String buscarCep(String cep) {
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }
	
	public static String getZona(String cep) {
		String json = buscarCep(cep);
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(json, Endereco.class);
		
		if(endereco.getBairro().equals("Alecrim")  || endereco.getBairro().equals("Areia Preta")  || endereco.getBairro().equals("Barro Vermelho")  || endereco.getBairro().equals("Bom Pastor")
				||endereco.getBairro().equals("Cidade Alta")  || endereco.getBairro().equals("Candel�ria")  || endereco.getBairro().equals("Capim Macio")
				|| endereco.getBairro().equals("Cidade da Esperan�a")  || endereco.getBairro().equals("Cidade Nova")  || endereco.getBairro().equals("Dix-Sept Rosado")
				||endereco.getBairro().equals("Felipe Camar�o")  || endereco.getBairro().equals("Guarapes")  || endereco.getBairro().equals("Lagoa Seca")  ||
				endereco.getBairro().equals("M�e Lu�za")  || endereco.getBairro().equals("Lagoa Nova") || endereco.getBairro().equals("Petr�polis")) {
			return "Zona 1";
		}else if(endereco.getBairro().equals("Igap�")||endereco.getBairro().equals("Nossa Senhora da Apresenta��o 	")|| endereco.getBairro().equals("Lagoa Azul")
				||endereco.getBairro().equals("Nossa Senhora de Nazar�")|| endereco.getBairro().equals("Paju�ara")|| endereco.getBairro().equals("Potengi")
				||endereco.getBairro().equals("Quintas")|| endereco.getBairro().equals("Redinha")||endereco.getBairro().equals("Ribeira")||
				endereco.getBairro().equals("Rocas")) {
			return "Zona 2";
		}else {
			return "Zona 3";
		}
	}

	public static String getBairro(String cep) {
		String json = buscarCep(cep);
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(json, Endereco.class);

		return endereco.getBairro();
	}
	
	public static String getLogradouro(String cep) {
		String json = buscarCep(cep);
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(json, Endereco.class);

		return endereco.getLogradouro();
	}
	
	public static String getUf(String cep) {
		String json = buscarCep(cep);
		Gson gson = new Gson();
		Endereco endereco = gson.fromJson(json, Endereco.class);

		return endereco.getUf();
	}

	public String getCartorio() {
		return cartorio;
	}
	
	public String cartorioZona() {
		System.out.println(cep);
		String zona = getZona(cep);
		if(zona.equals("Zona 1")) {
			cartorio= "3 Oficio de Notas";
		}else if(zona.equals("Zona 2")) {
			cartorio= "4 Oficio de Notas";
		}else {
			cartorio= "7 Oficio de Notas";
		}
		return cartorio;
	}
	
	public void setCep(String cep) {
		this.cep=cep;
	}
	
	public String getCep() {
		return cep;
	}
}
