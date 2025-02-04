
/**
 * Taken from https://github.com/javiergs/NielsenSkeleton
 *
 * This class represents a sample model for the application.
 * It is executed in a separate thread and updates the blackboard with the current time.
 *
 * @author javiergs
 * @version 1.0
 */
public class Engine implements Runnable {
	
	private boolean wait = true;
	private boolean stop = false;
	
	@Override
	public void run() {
		while (!stop) {
			try {
				Thread.sleep(1000);
				if (!wait) {
					Blackboard.getInstance().addValue("time", System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				// log the error
			}
		}
	}
	
	public void pause(boolean wait) {
		this.wait = wait;
	}
	
	public void stop(boolean stop) {
		this.stop = this.stop;
	}

}