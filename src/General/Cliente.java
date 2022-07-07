package General;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import Mensajes.MensajeCierre;
import Mensajes.MensajeConexion;
import Mensajes.MensajePedir;
import Mensajes.MensajeUsuarios;

public class Cliente {
	
	private static String nombreUsuario;
	private static String ip; // Ip del cliente
	private static String ipServer = "192.168.1.91";  // Ip del servidor
	private static Socket socket;
	private static Map<String, Fichero> ficheros;
	private static int puerto;
	private static ObjectOutputStream objectOutput;
	private static ObjectInputStream objectInput;
	private static String carpeta;
	private static Map<String, Fichero> ficherosNuevos;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		
		
		MiMonitor monitorConsola = new MiMonitor();
		
		ficherosNuevos = new HashMap<>();										// Map para almacenar los ficheros nuevos para en la desconexion incluirlo en la lista de ficheros
		
		getIP();  																// Tomamos la direccion IP de la máquina
		pedirDatosCliente();													// Pedimos el nombre de usuario al cliente
		carpeta = "ArchivosPC" + nombreUsuario;
		getFicheros();
		
		
		puerto = (int) Math.floor(Math.random()*(2000-500+1)+500);              // Generamos el puerto con un numero aleatorio.
		socket = new Socket(ipServer,2020);										// Creamos el socket con el servidor
		objectOutput = new ObjectOutputStream(socket.getOutputStream());        // Abrimos canales de entrada y salida
		objectInput = new ObjectInputStream(socket.getInputStream());
		
		objectOutput.writeObject(new MensajeConexion(nombreUsuario, nombreUsuario, new ArrayList<String>(ficheros.keySet()), ip, puerto));  // Lanzamos el mensaje de conexion
		objectOutput.flush();
		
		new Thread((new OyenteServidor(objectInput, objectOutput, nombreUsuario, ip, puerto, ficheros, ficherosNuevos, monitorConsola))).start();  // Lanzamos el OyenteServidor
		new Thread((new MenuUsuario(objectOutput, nombreUsuario, ficheros, "C:\\"+ carpeta + "\\archivos" + nombreUsuario + ".txt", monitorConsola))).start();  // Lanzamos el menu del usuario
		
		
	}

	private static void getFicheros() throws IOException {
		// TODO Auto-generated method stub
		Map<String, Fichero> tmpFicheros = new HashMap<>();
		File archivo = new File ("C:\\"+ carpeta + "\\archivos" + nombreUsuario + ".txt");
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		
		String linea;
        while((linea=br.readLine())!=null) {
            StringTokenizer st = new StringTokenizer(linea);
            
            while(st.hasMoreTokens()) {
            	Fichero tmp = new Fichero(st.nextToken());
            	File arch = new File("C:\\"+ carpeta +"\\" + tmp.getName());
            	FileReader fr2 = new FileReader (arch);
            	BufferedReader br2 = new BufferedReader(fr2);
            	String linea2, contenido2 = "";
            	while((linea2=br2.readLine())!=null) {
    			    StringTokenizer st2 = new StringTokenizer(linea2);
    			    while(st2.hasMoreTokens()) {
    			    	contenido2 += st2.nextToken() + " ";
    			    }
    			    contenido2 += "\n";
    			}
        		tmp.setContenido(contenido2);
            	tmpFicheros.put(tmp.getName(), tmp);	
            }
        }
        ficheros = tmpFicheros;
	}

	public static void pedirDatosCliente() {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Introduce tu nombre de cliente: ");
		nombreUsuario = scanner.nextLine();
	}
	
	
	public static void getIP() throws UnknownHostException, SocketException {
		try(final DatagramSocket socket = new DatagramSocket()){   
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  ip = socket.getLocalAddress().getHostAddress();
		}
	}
	
	
}
