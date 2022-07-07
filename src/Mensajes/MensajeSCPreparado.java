package Mensajes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import General.Fichero;
import General.Usuario;

public class MensajeSCPreparado extends Mensaje implements Serializable{
	
	private String ip;
	private int puerto;

	public MensajeSCPreparado(String origen, String destino, String ip, int puerto) {
		super(3, origen, destino);
		this.puerto = puerto;
		this.ip = ip;
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
	public Fichero getFichero() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIP() {
		// TODO Auto-generated method stub
		return ip;
	}

	@Override
	public int getPuerto() {
		// TODO Auto-generated method stub
		return puerto;
	}

	@Override
	public Map<String, String> getUsuarios() {
		// TODO Auto-generated method stub
		return null;
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
