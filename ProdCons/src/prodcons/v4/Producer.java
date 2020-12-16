package prodcons.v4;


public class Producer extends Thread {
	
	ProdConsBuffer buffer;
	int minProd, maxProd;
	int nbMessage;
	int prodTime;
	int maxEx;
	
	public Producer(int min, int max, int time, ProdConsBuffer buff, int maxExam) {
		
		minProd = min;
		maxProd = max;
		prodTime = time;
		buffer = buff;
		nbMessage = (int) (Math.random() * (maxProd - minProd) + minProd);
		maxEx = maxExam;
		
	}
	
	public void run() {
		
		try {
			for (int i = 0; i < nbMessage; i++) {	
				Message m;
				if(Math.random() >= 0.95) {
					int n =  (int)(Math.random() * (maxEx - 1) + 2);
					m = new Message("Message n°" + i + " of Thread n°" + this.getId(), n);
					produce(m, n);
				} else {
					m = new Message("Message n°" + i + " of Thread n°" + this.getId(), 1); 
					produce(m, 1);
				}
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
	
	public Message produce(Message m, int n) throws InterruptedException {
		sleep(prodTime);
		buffer.put(m, n);
		return m;
	}

	public int getNbMessage() {
		return nbMessage;
	}

}
