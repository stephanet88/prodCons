package prodcons.v1;

import java.util.Random;

public class Consumer extends Thread {
	
	int consTime;
	ProdConsBuffer buffer;
	
	static Object wait = new Object();
	
	public Consumer(int time, ProdConsBuffer buff) {
		super();
		Random r = new Random();
		consTime = (int) (time + r.nextGaussian());
		buffer = buff;
	}
	
	public void run() {
		try {
			while(true) {
				sleep(consTime);
				consume();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return;
		}
		
	}
	
	public void consume() throws InterruptedException {
		Message m = buffer.get();
		if (m == null)
			System.out.println(Thread.currentThread().getId());
	}

}
