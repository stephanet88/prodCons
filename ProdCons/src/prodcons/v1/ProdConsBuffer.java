package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {
	
	TestProdCons tester;
	Message[] buffer;
	int prod, cons;
	int nbConsumed;

	public ProdConsBuffer(int tailleBuffer, TestProdCons test) {
		buffer = new Message[tailleBuffer];
		prod = 0;
		cons = 0;
		nbConsumed	= 0;
		tester = test;
	}

	@Override
	public synchronized void put(Message m) {
		while (!canProduce()) {
			try { wait();}
			catch(InterruptedException e) {}
		}
		buffer[prod] = m;
		prod++;
		prod = prod % buffer.length;
	}

	@Override
	public synchronized Message get() {
		Message m = buffer[cons];
		if(m!=null) {
			buffer[cons] = null;
			cons++;
			cons = cons % buffer.length;
			nbConsumed++;
			System.out.println("nbConsumed : " + nbConsumed);
			notify();
		}
		return m;
	}

	@Override
	public int nmsg() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totmsg() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean canProduce() {
		return buffer[prod] == null;
	}
	
	public int getNbConsumed() {
		return nbConsumed;
	}
	
	public boolean doStop() {
		return tester.doStop();
	}
	
}
