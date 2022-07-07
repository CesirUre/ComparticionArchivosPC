package General;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Emisor implements Runnable {
	
	private int puerto;
	private ServerSocket listen;
	private String fichero;
	private Map<String, Fichero> ficheros;
	
	public Emisor(int puerto, String fichero, Map<String, Fichero> ficheros) throws IOException {
		this.puerto = puerto;
		this.fichero = fichero;
		this.ficheros = ficheros;
		listen = new ServerSocket(puerto);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socket = null;
		ObjectOutputStream objectOutput = null;
		try {
			socket = listen.accept();
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectOutput.writeObject(ficheros.get(fichero));
			objectOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
