package prodcons.v1;

public class Producer extends Thread {
	
	int minProd, maxProd;
	int prodTime;
	int nbMessage;
	ProdConsBuffer buffer;
	static Object wait = new Object();
	
	public Producer(int min, int max, int time, ProdConsBuffer buff) {
		super();
		
		minProd = min;
		maxProd = max;
		prodTime = time;
		nbMessage = (int) (Math.random() * (maxProd - minProd) + minProd);
		buffer = buff;
		
	}
	
	public void run() {

			try {
								
				for (int i = 0; i < nbMessage; i++) {
					produce(new Message("Message n°" + i + " of Thread n°" + this.getId()));
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public Message produce(Message m) throws InterruptedException {
		sleep(prodTime);
		buffer.put(m);
		return m;
	}
	
	public int getNbMessage() {
		return nbMessage;
	}

}
