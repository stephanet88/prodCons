package prodcons.v4;

public class Consumer extends Thread {
	
	int consTime;
	
	public Consumer(int time) {
		
		consTime = time;
		
	}
	
	public void run() {
		while(true) {
			try {
				//...
				Thread.sleep(consTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void consume(Message m) {
		
	}

}
