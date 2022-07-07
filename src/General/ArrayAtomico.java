package General;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ArrayAtomico {
	private AtomicIntegerArray atArray;
	
	public ArrayAtomico(int nProcesses) {
		initialize(nProcesses);
	}
	
	private void initialize(int nProcesses) {
		atArray = new AtomicIntegerArray(nProcesses);
		for (int i = 0; i < nProcesses; i++) {
			atArray.set(i, -1);
		}
	}

	AtomicIntegerArray getArrayAtomico() {
		return atArray;
	}
}
