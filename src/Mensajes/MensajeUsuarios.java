package Mensajes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import General.Fichero;
import General.Usuario;


public class MensajeUsuarios extends Mensaje implements Serializable{
	
	private Map<String, String> usuarios;
	
	public MensajeUsuarios(String origen, String destino, Map<String, String> usuarios) {
		super(1, origen, destino);
		// TODO Auto-generated constructor stub
		this.usuarios = usuarios;
	}

	@Override
	public int getTipo() {
		// TODO Auto-generated method stub
		return tipo;
	}

	@Override
	public String getOrigen() {
		// TODO Auto-generated method stub
		return origen;
	}

	@Override
	public String getDestino() {
		// TODO Auto-generated method stub
		return destino;
	}
	
	@Override
	public Map<String, String> getUsuarios() {
		// TODO Auto-generated method stub
		return usuarios;
	}

	@Override
	public Fichero getFichero() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPuerto() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFicheroNombre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getFicheros() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
