package prodcons.v2;

public class Consumer extends Thread {
	
	int consTime;
	ProdConsBuffer buffer;
	
	public Consumer(int time, ProdConsBuffer buff) {
		
		consTime = time;
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
			e.printStackTrace();
			return;
		}
		
	}
	
	public void consume() throws InterruptedException {
		buffer.get();
	}

}
