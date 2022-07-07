package Mensajes;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import General.Fichero;
import General.Usuario;

public class MensajeEmitir extends Mensaje implements Serializable {
	
	private String fichero;
	
	public MensajeEmitir(String origen, String destino, String fichero) {
		super(2, origen, destino);
		this.fichero = fichero;
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

	
	public Fichero getFichero() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setFichero(String fichero) {
		this.fichero = fichero;
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
		return fichero;
	}


	@Override
	public List<String> getFicheros() {
		// TODO Auto-generated method stub
		return null;
	}

}
