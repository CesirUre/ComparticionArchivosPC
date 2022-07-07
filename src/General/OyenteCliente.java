package General;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import Mensajes.Mensaje;
import Mensajes.MensajeCierre;
import Mensajes.MensajeConexion;
import Mensajes.MensajeConfirmacionCierre;
import Mensajes.MensajeEmitir;
import Mensajes.MensajeNoDisponible;
import Mensajes.MensajeSCPreparado;
import Mensajes.MensajeUsuarios;

public class OyenteCliente implements Runnable {
	
	private Map<String, Usuario> usuarios;    // Mapa de usuarios con su informacion
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private String id;
	private Map<String, List<Usuario>> listaFicheros;  // Mapa de los ficheros y los usuarios que lo tienen para poder transmitirlo
	private LockRompeEmpate lockTabla;
	private ArrayAtomico in;
	private ArrayAtomico last;
	private int idlock;
	private Semaphore write;
	private boolean[] idAv;
	
	
	public OyenteCliente(Socket socket, Map<String, Usuario> usuarios, Map<String, List<Usuario>> listaFicheros, LockRompeEmpate lockTabla, ArrayAtomico in, ArrayAtomico last, int idlock, Semaphore write, boolean [] idAv) throws IOException, ClassNotFoundException {
		objectInput = new ObjectInputStream(socket.getInputStream());			// Inicializamos los canales de transmision de datos con el servidor
		objectOutput = new ObjectOutputStream(socket.getOutputStream());
		this.usuarios = usuarios;
		this.listaFicheros = listaFicheros;
		this.lockTabla = lockTabla;
		this.in = in;
		this.last = last;
		this.idlock = idlock;
		this.write = write;
		this.idAv = idAv;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean fin = false;
		while(!fin) {
			
			Mensaje m = null;
			try {
				
				m = (Mensaje) objectInput.readObject();
				
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}         // Esperamos a que nos llegue el mensaje y dependendiendo del tipo realizamos un accion determinada
			
			
			switch(m.getTipo()) {
				case 0: 					//Mensaje de conexion  (Necesita concurrencia)
				id = m.getOrigen();
				lockTabla.takeLock(idlock, in.getArrayAtomico(), last.getArrayAtomico(), usuarios.size());
				usuarios.get(id).setOnline();
				usuarios.get(id).setInputStream(objectInput);      // Guardamos la información del usuario en la tabla
				usuarios.get(id).setOutputStream(objectOutput);
				usuarios.get(id).setIP(m.getIP());
				usuarios.get(id).setPuerto(m.getPuerto());
				for (String s : m.getFicheros()) {
					listaFicheros.get(s).add(usuarios.get(m.getOrigen()));
				}
				lockTabla.releaseLock(in.getArrayAtomico(), idlock);
					
					Mensaje envio = new MensajeConexion(id, id, null, m.getIP(), m.getPuerto());
				try {
					objectOutput.writeObject(envio);      // Enviamos mensaje de confirmacion de conexion al usuario a traves del output
					objectOutput.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			        
					break;
				case 1:
					// Creamos un mensaje que contenga la lista de usuarios para mandarsela al cliente
					lockTabla.takeLock(idlock, in.getArrayAtomico(), last.getArrayAtomico(), usuarios.size());
					List<String> usernames = new ArrayList<String>(usuarios.keySet());
					Map<String,String> ficheros = new HashMap<>();
					for (int i = 0; i < usuarios.size(); i++) {
						if (usuarios.get(usernames.get(i)).getEstado()) {
							List<Fichero> fich = usuarios.get(usernames.get(i)).getListaFicheros();
							String sf = "";
							for (int j = 0; j < fich.size(); j++) {
								if (j < fich.size()-1) {
									sf += fich.get(j).getName() + ", ";
								}
								else {
									sf += fich.get(j).getName();
								}
							}
							ficheros.put(usernames.get(i), sf);
						}
					}
					lockTabla.releaseLock(in.getArrayAtomico(), idlock);
					try {
						objectOutput.writeObject(new MensajeUsuarios(id, id, ficheros));
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case 2:
					
					lockTabla.takeLock(idlock, in.getArrayAtomico(), last.getArrayAtomico(), usuarios.size());
					usuarios.get(id).setOffline(); 						// Eliminamos (ponemos a falso el atributo online) al usuario de conectados de la tabla
					usuarios.get(id).setEnviando(false);
					idAv[idlock] = false;
					File ficheroNuevo = new File ("C:\\ArchivosPC\\users.txt" );
					
					try {
						  write.acquire();
						  ficheroNuevo.createNewFile();
						  BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroNuevo));
						  List<String> usuariosl = new ArrayList<String>(usuarios.keySet());
						  for (int i = 0; i < usuarios.size(); i++) {
							  bw.write(usuarios.get(usuariosl.get(i)).getId() + " ");
							  for (int j = 0; j < usuarios.get(usuariosl.get(i)).getListaFicheros().size(); j++) {
								  bw.write(usuarios.get(usuariosl.get(i)).getListaFicheros().get(j).getName() + " ");
							  }
							  bw.write("\n");
						  }
						  bw.close();
						  write.release();
					} catch (IOException | InterruptedException ioe) {
						  ioe.printStackTrace();
					}
					lockTabla.releaseLock(in.getArrayAtomico(), idlock);
					Mensaje envio2 = new MensajeConfirmacionCierre(id,id);
					
					try {
						objectOutput.writeObject(envio2);				// Enviamos mensaje de confirmacion de cierre de conexion
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fin = true;
					break;
				case 3:
					ObjectOutputStream tmpOutput = null;
					if (listaFicheros.get(m.getFicheroNombre()).size() > 1) {  	// Si más de un cliente tiene el fichero, se intenta conectar con el que no esta transmitiendo
						for (int i = 0; i < listaFicheros.get(m.getFicheroNombre()).size(); i++) {  // Tomamos el primero que tenga el fichero y no este enviando
							if(listaFicheros.get(m.getFicheroNombre()).get(i).getEstado()) {  // Comprobamos si esta conectado y si esta enviando
								tmpOutput = listaFicheros.get(m.getFicheroNombre()).get(i).getOutputStream();
								break;
							}	
						}
					}
					else if(listaFicheros.get(m.getFicheroNombre()).size() == 0) {
							
							try {
								Mensaje envio3 = new MensajeNoDisponible(id,id);   // Pedimos al cliente que tenga el fichero que lo mande al cliente que lo ha pedido
								objectOutput.writeObject(envio3);
								objectOutput.flush();	
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						}
					
					else {
						tmpOutput = listaFicheros.get(m.getFicheroNombre()).get(0).getOutputStream();
					}
					lockTabla.takeLock(idlock, in.getArrayAtomico(), last.getArrayAtomico(), usuarios.size());
					usuarios.get(m.getOrigen()).setFichero(new Fichero(m.getFicheroNombre()));
					lockTabla.releaseLock(in.getArrayAtomico(), idlock);
					try {
						Mensaje envio3 = new MensajeEmitir(listaFicheros.get(m.getFicheroNombre()).get(0).getId(), m.getOrigen(), m.getFicheroNombre());   // Pedimos al cliente que tenga el fichero que lo mande al cliente que lo ha pedido
						tmpOutput.writeObject(envio3);
						tmpOutput.flush();	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					ObjectOutputStream tmpOutput2 = null;
					Mensaje envio4 = new MensajeSCPreparado(id, m.getDestino(), m.getIP(), m.getPuerto());  // Nos llega la ip y el puerto del cliente que tiene el archivo y se lo pasamos al cliente que lo quiere.
					tmpOutput2 = usuarios.get(m.getDestino()).getOutputStream();
					try {
						tmpOutput2.writeObject(envio4);
						tmpOutput2.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
			
			}
		}
	}
}
