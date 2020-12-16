package prodcons.v2;

import java.util.Random;

public class Consumer extends Thread {
	
	int consTime, ecart = 1;
	ProdConsBuffer buffer;
	
	public Consumer(int time, ProdConsBuffer buff) {		
		Random r = new Random();
		consTime = (int) (time + r.nextGaussian() * ecart);
		if (consTime < 0) {
			consTime = 0;
		}
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
