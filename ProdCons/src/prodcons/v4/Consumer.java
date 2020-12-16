package prodcons.v4;

import java.util.Random;

public class Consumer extends Thread {
	
	int consTime, m_nReadings;
	ProdConsBuffer buffer;
	
	public Consumer(int time, ProdConsBuffer buff, int minLec, int maxLec) {
		Random r = new Random();
		consTime = (int) (time + r.nextGaussian());
		m_nReadings = (int) (Math.random() * (maxLec - minLec) + minLec);
		buffer = buff;
	}
	
	public void run() {
		while(true) {
			try {
				sleep(consTime);
				consume(); // On ne peut pas appeler consume(k) dans cette version car ce n'est pas compatible avec put(m, n)
			} catch (InterruptedException e) {
				//e.printStackTrace();
				return;
			}
		}
	}
	
	public void consume() throws InterruptedException {
		Message m = buffer.get();
		if (m == null)
			System.out.println(Thread.currentThread().getId());
	}	
	
	public void consume(int k) throws InterruptedException {
		Message [] m = buffer.get(k);
		for (int i = 0; i < k; i++) {
			if (m[i] == null)
				System.out.println(Thread.currentThread().getId());
		}
	}	

}
