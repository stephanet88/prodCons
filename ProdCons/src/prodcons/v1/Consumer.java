package prodcons.v1;

public class Consumer extends Thread {
	
	int consTime;
	ProdConsBuffer buffer;
	
	static Object wait = new Object();
	
	public Consumer(int time, ProdConsBuffer buff) {
		super();
		
		consTime = time;
		buffer = buff;
		
	}
	
	public void run() {
		while(true) {
			try {
				if(buffer.doStop()) {
					buffer.tester.stopAll();
					return;
				}
				Thread.sleep(consTime);
				consume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void consume() throws InterruptedException {
		buffer.get();
	}

}
