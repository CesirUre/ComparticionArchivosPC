package General;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class Receptor implements Runnable {
	
	private Socket socket;
	private ObjectInputStream inputStream;
	private Map<String,Fichero> ficheros;
	private String id;

	public Receptor(String ip, int puerto, String id, Map<String,Fichero> ficheros) throws UnknownHostException, IOException {
		socket = new Socket(ip, puerto);
		inputStream = new ObjectInputStream(socket.getInputStream());
		this.id = id;
		this.ficheros = ficheros;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Fichero ficheroReceptor = null;
		try {
			ficheroReceptor = (Fichero) inputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Fichero fichero = new Fichero(ficheroReceptor.getName());  // Creamos el fichero

		fichero.setContenido(ficheroReceptor.getContenido());   // Metemos el contenido del fichero
		
		ficheros.put(fichero.getName(), fichero);   // Lo incluimos en el mapa de los ficheros del cliente
		
		File ficheroNuevo = new File ("C:\\ArchivosPC" + id + "\\" + fichero.getName());
		
		try {
			  ficheroNuevo.createNewFile();
			  BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroNuevo));
			  bw.write(ficheroReceptor.getContenido());
			  bw.close();
		} catch (IOException ioe) {
			  ioe.printStackTrace();
		}
		
	}

}
