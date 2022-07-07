package Mensajes;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import General.Fichero;


public abstract class Mensaje implements Serializable {
	
	protected int tipo;
	protected String origen;
	protected String destino;
	
	public Mensaje(int tipo, String origen, String destino) {
		this.destino = destino;
		this.origen = origen;
		this.tipo = tipo;
	}
	
	public abstract int getTipo();
	public abstract String getOrigen();
	public abstract String getDestino();
	public abstract Fichero getFichero();
	public abstract List<String> getFicheros();
	public abstract String getIP();
	public abstract String getFicheroNombre();
	public abstract int getPuerto();
	public abstract Map<String, String> getUsuarios();
	
}
