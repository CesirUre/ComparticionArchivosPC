package General;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicIntegerArray;

import Mensajes.MensajeCierre;
import Mensajes.MensajePedir;
import Mensajes.MensajeUsuarios;

public class MenuUsuario implements Runnable {
	
	private ObjectOutputStream objectOutput;
	private String nombreUsuario;
	private Map<String, Fichero> ficheros;
	private String ficheroArchivos;
	private MiMonitor monitorConsola;
	
	public MenuUsuario(ObjectOutputStream objectOutput, String nombreUsuario, Map<String, Fichero> ficheros, String ficheroArchivos, MiMonitor monitorConsola) {
		this.nombreUsuario = nombreUsuario;
		this.objectOutput = objectOutput;
		this.ficheros = ficheros;
		this.ficheroArchivos = ficheroArchivos;
		this.monitorConsola = monitorConsola;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String opcion = "";
		boolean fin = false;
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		while(!fin) {
			if(opcion == "" || Integer.parseInt(opcion) == 1) {  // Para enseñar la tabla, esperamos a que la imprima para elegir otra opcion
				try {
					monitorConsola.menu();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			else {  							// Si el cliente ha elegido descargar el fichero entonces puede elegir más opciones mientras se descarga el fichero
				System.out.println("\n1 - Consultar lista de usuarios");
				System.out.println("2 - Descargar un fichero");
				System.out.println("3 - Salir del servidor");
				System.out.print("Seleccione el número de la acción que quiere realizar: ");
			}
			opcion = scanner.nextLine();
			
			switch(Integer.parseInt(opcion)) {
				case 1:
					try {
						objectOutput.writeObject(new MensajeUsuarios(nombreUsuario, nombreUsuario, null));
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					
					break;
				case 2:
					System.out.print("Introduce el nombre del fichero que quieres descargar: ");
					String fichero = scanner.nextLine();
					try {
						objectOutput.writeObject(new MensajePedir(nombreUsuario, nombreUsuario, fichero));
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
						
					break;
				case 3:
					
					try {
						File archivo = new File(ficheroArchivos);
						BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
						
						for (String s : new ArrayList<String>(ficheros.keySet())) {
							bw.write(s + " ");
						}
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						objectOutput.writeObject(new MensajeCierre(nombreUsuario, nombreUsuario));
						objectOutput.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					
					fin = true;
					break;
			}
		}
		
	}

}
