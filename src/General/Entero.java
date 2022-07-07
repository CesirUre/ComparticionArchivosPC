package General;

public class Entero {
	volatile int global;
	
	public Entero() {
		global = 0;
	}
	
	public void setEntero(int n) {
		global = n;
	}
	
	public int getEntero() {
		return global;
	}
}
