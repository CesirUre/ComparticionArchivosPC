package General;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;

import Mensajes.Mensaje;
import Mensajes.MensajeCSPreparado;

public class OyenteServidor implements Runnable {
	
	private String id;
	private String ip;
	private int puerto;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private Map<String, Fichero> ficheros;
	private MiMonitor monitorConsola;

	public OyenteServidor(ObjectInputStream objectInput, ObjectOutputStream objectOutput, String id, String ip, int puerto, Map<String, Fichero> ficheros, Map<String, Fichero> ficherosNuevos, MiMonitor monitorConsola) throws IOException {
		this.ficheros = ficheros;
		this.id = id;
		this.ip = ip;
		this.puerto = puerto;
		this.objectInput = objectInput;			// Inicializamos los canales de transmision de datos con el servidor
		this.objectOutput = objectOutput;
		this.monitorConsola = monitorConsola;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean fin = false;
		while(!fin) {
			Mensaje m = null;
			try {
				m = (Mensaje) objectInput.readObject();         // Esperamos a que nos llegue el mensaje y dependendiendo del tipo realizamos un accion determinada
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(m.getTipo()) {
				case 0:
					
					try {
						monitorConsola.contenidoConexion();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				case 1:
					Map<String, String> tmpMap = m.getUsuarios();
					List<String> l = new ArrayList<String>(tmpMap.keySet());
					
					try {
						monitorConsola.contenidoUsuarios(tmpMap, l);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					break;
				case 2:
					Mensaje envio2 = new MensajeCSPreparado(id, m.getDestino(), ip, puerto);
					try {
						objectOutput.writeObject(envio2);    // Enviamos mensaje cliente servidor preparado
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						(new Emisor(puerto, m.getFicheroNombre(), ficheros)).run();          // Iniciamos el emisor
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 3:
					try {
						(new Receptor(m.getIP(), m.getPuerto(), m.getDestino(), ficheros)).run();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//monitorConsola.contenidoDescargar();
					break;
				case 4:
					
					try {
						monitorConsola.contenidoDesconexion();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					fin = true;
					break;
				case 5:
					
					try {
						monitorConsola.contenidoFicheroNoDisponible();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
			}
		}

	}
}
