package prodcons.v4;


public class Producer extends Thread {
	
	ProdConsBuffer buffer;
	int minProd, maxProd;
	int nbMessage;
	int prodTime;
	int maxEx;
	
	public Producer(int min, int max, int time, ProdConsBuffer buff, int maxE) {
		
		minProd = min;
		maxProd = max;
		prodTime = time;
		buffer = buff;
		nbMessage = (int) (Math.random() * (maxProd - minProd) + minProd);
		maxEx = maxE;
		
	}
	
	public void run() {
		
		try {
			for (int i = 0; i < nbMessage; i++) {	
				Message m = new Message("Message n°" + i + " of Thread n°" + this.getId());
				produce(m);
			}
		} catch (InterruptedException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Message produce(Message m) throws InterruptedException {
		sleep(prodTime);
		if(Math.random() > 0.95) {
			buffer.put(m, (int)(Math.random() * maxEx + 1) );
		} else {
			buffer.put(m);
		}
		return m;
	}

	public int getNbMessage() {
		return nbMessage;
	}

}
