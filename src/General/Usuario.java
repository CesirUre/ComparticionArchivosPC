package General;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable{
	
	private String id;
	private String ip;
	private List<Fichero> ficheros;
	private boolean online;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private boolean enviando;
	private int puerto;
	
	public Usuario(String id, String ip, List<Fichero> ficheros, int puerto) {
		this.ficheros = ficheros;
		this.id = id;
		this.ip = ip;
		this.online = false;
		this.enviando = false;
		this.puerto = puerto;
	}
	
	public boolean getEnviando() {
		return enviando;
	}
	
	public void setEnviando(boolean enviando) {
		this.enviando = enviando;
	}
	
	public String getId() {
		return id;
	}
	
	public String getIP() {
		return ip;
	}
	
	public Fichero getFichero(String nombre) {
		for (int i = 0; i < ficheros.size(); i++) {
			if (ficheros.get(i).getName().equals(nombre)) {
				return ficheros.get(i);
			}
		}
		return null;
	}
	
	public void setOnline() {
		online = true;
	}
	
	public void setOffline() {
		online = false;
	}
	
	public boolean getEstado() {
		return online;
	}
	
	public void setInputStream(ObjectInputStream objectInput) {
		this.objectInput = objectInput;
	}
	
	public void setOutputStream(ObjectOutputStream objectOutput) {
		this.objectOutput = objectOutput;
	}
	
	public ObjectInputStream getInputStream() {
		return objectInput;
	}
	
	public ObjectOutputStream getOutputStream() {
		return objectOutput;
	}

	public List<Fichero> getListaFicheros() {
		return ficheros;
	}

	public int getPuerto() {
		return puerto;
	}
	
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public void setIP(String ip) {
		// TODO Auto-generated method stub
		this.ip = ip;
	}

	public void setFichero(Fichero fich) {
		// TODO Auto-generated method stub
		ficheros.add(fich);
	}
}
