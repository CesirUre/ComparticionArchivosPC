package General;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;


public class Servidor {
	
	private static Map<String, Usuario> usuarios;    		// Mapa para llevar todos los clientes del servidor con sus direcciones y ficheros y saber si estan online. Identificados con su propio identificador
	private static Map<String, List<Usuario>> listaFicheros;
	private static ServerSocket listen;
	private static int puerto = 2020;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		listaFicheros = new HashMap<>();
		listen = new ServerSocket(puerto);
		
		
		// Leer los usuarios registrados en el servidor y meter todos sus datos en la tabla
		getUsuarios();
		
		// Array para saber que id del lock esta disponible (implementado ya que al entrar con el mismo cliente a la misma sesion del server, no habia id's para el lock disponibles)
		boolean [] idAv = new boolean[usuarios.size()];
		// Lock y sus elementos para leer y modificar la tabla de uno en uno si varios lo piden a la vez.
		LockRompeEmpate lockTabla = new LockRompeEmpate();
		ArrayAtomico in = new ArrayAtomico(usuarios.size());
		ArrayAtomico last = new ArrayAtomico(usuarios.size());
		int idlock = 0;
		
		// Socket para recibir la conexion
		Socket socket;
		// Semáforo para escribir en el fichero solo un oyente cliente al mismo tiempo
		Semaphore write = new Semaphore(1);		// Al leer no hace falta ya que solo lee el servidor una vez y no ha llegado ningun cliente todavia con lo que no puede haber nadie mas leyendo o escribiendo en el fichero
		
		while(true) {
			
			socket = listen.accept();
			
			
			new Thread((new OyenteCliente(socket, usuarios, listaFicheros, lockTabla, in, last, idlock, write, idAv))).start();
			
			idAv[idlock] = true;
			while (true) {   //Hasta que no haya un id para el lock seguimos buscando
				idlock = (idlock + 1) % usuarios.size();
				if (!idAv[idlock]) {
					idAv[idlock] = true;
					break;
				}
			}
		    
		}
	}

	private static void getUsuarios() throws IOException {
		// TODO Auto-generated method stub
		usuarios = new HashMap<>();
		// Leemos el fichero que tiene el siguiente formato: cada usuario es una linea, cada linea tiene nombre y luego todos los ficheros que contiene separadosCesa con un espacio.
		File archivo = new File ("C:\\ArchivosPC\\users.txt");
		FileReader fr = new FileReader (archivo);
		BufferedReader br = new BufferedReader(fr);
		
		String linea, nombre, ip;
		int puerto;
        while((linea=br.readLine())!=null) {
            StringTokenizer st = new StringTokenizer(linea);
            List<Fichero> ficheros = new ArrayList<>();
            
            nombre = st.nextToken();
            while(st.hasMoreTokens()) {
            	Fichero tmp = new Fichero(st.nextToken());
            	ficheros.add(tmp);
            }
            Usuario u = new Usuario(nombre, null, ficheros, 0);
            usuarios.put(nombre, u);
            
            for (int i = 0; i < ficheros.size(); i++) {
            	if (!listaFicheros.containsKey(ficheros.get(i).getName())) {
            		List<Usuario> usu = new ArrayList<>();
            		listaFicheros.put(ficheros.get(i).getName(), usu);
            	}
            	
            }
        }
	}
	
}
