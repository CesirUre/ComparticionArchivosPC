package General;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class LockRompeEmpate {
	
	public void releaseLock(AtomicIntegerArray in, int id) {
		in.set(id, -1);
	}
	
	public void takeLock(int id, AtomicIntegerArray in, AtomicIntegerArray last, int procesos) {
		for (int i = 0; i < procesos; i++) {
			in.set(id, i);
			last.set(i, id);
			for (int j = 0; j < procesos; j++) {
				if (j != id) {
					while(in.get(j) >= in.get(id) && last.get(i) == id);
				}
			}
		}
		
	}
}
