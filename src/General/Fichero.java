package General;
import java.io.Serializable;

public class Fichero implements Serializable {
	private String name;
	private String contenido;
	
	public Fichero(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		// TODO Auto-generated method stub
		this.contenido = contenido;
	}
}
