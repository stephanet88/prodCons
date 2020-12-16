package prodcons.v2;

import java.util.Random;

public class Consumer extends Thread {
	
	int consTime;
	ProdConsBuffer buffer;
	
	public Consumer(int time, ProdConsBuffer buff) {		
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
			System.out.println("id : " + Thread.currentThread().getId() + " est null");
	}

}
