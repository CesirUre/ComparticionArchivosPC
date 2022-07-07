package General;
import java.util.List;
import java.util.Map;

public class MiMonitor {
	
	//private final Condition menu = l.newCondition(); // Utilizamos los condition en la version de lock & condition
	private boolean c;
	
	public MiMonitor() {
		c = false;
	}
	
	public synchronized void menu() throws InterruptedException {
		if (!c) {
			wait();
		}
		
		System.out.println("1 - Consultar lista de usuarios");
		System.out.println("2 - Descargar un fichero");
		System.out.println("3 - Salir del servidor");
		System.out.print("Seleccione el número de la acción que quiere realizar: ");
		c = false;
	}
	
	public synchronized void contenidoUsuarios(Map<String, String> tmpMap, List<String> ls) throws InterruptedException {
		c = true;
		String leftAlignFormat = "| %-24s | %-50s |%n";

		System.out.format(" -------------------------------------------------------------------------------%n");
		System.out.format("|          Client          |                      Ficheros                      |%n");
		System.out.format("|--------------------------|----------------------------------------------------|%n");
		
		for (int i = 0; i < tmpMap.size(); i++) {
			System.out.format(leftAlignFormat, ls.get(i), tmpMap.get(ls.get(i)));
		}
		
		System.out.format(" -------------------------------------------------------------------------------\n%n");
		
		notifyAll();	
	}
	
	public synchronized void contenidoConexion() throws InterruptedException {
		c = true;
		System.out.println("  ----------------------------------------------------------------------------------");
		System.out.println(" |                             ------------------------                             |");
		System.out.println(" |----------------------------| BIENVENIDO AL SERVIDOR |----------------------------|");
		System.out.println(" |                             ------------------------                             |");
		System.out.println("  ----------------------------------------------------------------------------------\n");
		notifyAll();
	}
	
	public synchronized void contenidoDesconexion() throws InterruptedException {
		c = true;
		System.out.println("  ----------------------------------------------------------------------------------");
		System.out.println(" |                             ------------------------                             |");
		System.out.println(" |----------------------------| ¡¡¡HASTA LA PRÓXIMA!!! |----------------------------|");
		System.out.println(" |                             ------------------------                             |");
		System.out.println("  ---------------------------------------------------------------------------------- ");
		notifyAll();
	}
	
	public synchronized void contenidoFicheroNoDisponible() throws InterruptedException {
		c = true;
		System.out.println("El fichero que quiere descargar no esta disponible. Inténtelo de nuevo.");
		notifyAll();
	}

	/*public synchronized void contenidoDescargar() {
		// TODO Auto-generated method stub
		c = true;
		System.out.println("\n Descargando fichero... \n");
		notifyAll();
	}*/
	
}
