package prodcons.v2;


public class Producer extends Thread {
	
	ProdConsBuffer buffer;
	int minProd, maxProd;
	int nbMessage;
	int prodTime;
	
	public Producer(int min, int max, int time, ProdConsBuffer buff) {
		
		minProd = min;
		maxProd = max;
		prodTime = time;
		buffer = buff;
		nbMessage = (int) (Math.random() * (maxProd - minProd) + minProd);
		
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
