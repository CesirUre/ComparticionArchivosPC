package General;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hilo extends Thread {
	
	private LockRompeEmpate lock;
	private int id;
	private ArrayAtomico in;  	
	private ArrayAtomico last;
	private Entero procesos;
	private Socket socket;
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	private List<Fichero> ficheros;
	
	public Hilo(LockRompeEmpate l, int id, ArrayAtomico in, ArrayAtomico last, Entero procesos, Socket socket, List<Fichero> ficheros) throws IOException {
		lock = l;
		this.id = id;
		this.in = in;  		
		this.last = last;
		this.procesos = procesos;
		this.socket = socket;
		this.ficheros = ficheros;
		
		try {
			objectOutput = new ObjectOutputStream(socket.getOutputStream());
			objectInput = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		String nombre = null;
		try {
			objectOutput.writeObject("Hola Cliente");
			objectOutput.flush();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
		try {
			System.out.println((String)objectInput.readObject());
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			nombre = (String)objectInput.readObject();
			System.out.println(nombre);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//lock.takeLock(id, in.getArrayAtomico(), last.getArrayAtomico(), procesos.getEntero());
		while (!nombre.equals("Exit")) {
	    
	    
		    for (Fichero f : ficheros) {
		    	if (nombre.equals(f.getName())) {
						try { 
							objectOutput.writeObject(f);
							objectOutput.flush();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	}
		    
			}
		    
		    try {
				nombre = (String)objectInput.readObject();
				System.out.println(nombre);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//lock.releaseLock(in.getArrayAtomico(), id);
		}
	}
}
