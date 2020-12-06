package prodcons.v2;

public class Producer extends Thread {
	
	int minProd, maxProd;
	int prodTime;
	
	public Producer(int min, int max, int time) {
		
		minProd = min;
		maxProd = max;
		prodTime = time;
		
	}
	
	public void run() {
		
		int nbMessage = (int) (Math.random() * (maxProd - minProd) + minProd);
		
	}
	
	public void produce(Message m) {
		
	}

}
