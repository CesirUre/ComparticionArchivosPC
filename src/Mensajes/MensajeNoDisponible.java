package Mensajes;

import java.util.List;
import java.util.Map;

import General.Fichero;

public class MensajeNoDisponible extends Mensaje {

	public MensajeNoDisponible(String origen, String destino) {
		super(5, origen, destino);
		// TODO Auto-generated constructor stub
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
		return null;
	}

	@Override
	public int getPuerto() {
		// TODO Auto-generated method stub
		return 0;
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
