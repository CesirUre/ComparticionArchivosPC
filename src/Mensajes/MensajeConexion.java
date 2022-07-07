package Mensajes;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import General.Fichero;
import General.Usuario;

public class MensajeConexion extends Mensaje implements Serializable {
	
	private List<String> ficheros;
	private int puerto;
	private String ip;
	
	public MensajeConexion(String origen, String destino, List<String> ficheros, String ip, int puerto) {
		super(0, origen, destino);
		// TODO Auto-generated constructor stub
		this.ficheros = ficheros;
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
		return ficheros;
	}

	

}
