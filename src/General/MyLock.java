package General;

public class MyLock {
	private volatile int lock = 0;

	public void releaseLock() {
		if (lock == 0) {
			lock = 1;
		}
		else {
			lock = 0;
		}
	}
	public void takeLock(int n) {
		lock = n;
	}
	public int getLock() {
		return lock;
	}
}
