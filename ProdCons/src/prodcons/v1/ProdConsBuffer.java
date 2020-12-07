package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer {
	
	TestProdCons tester;
	Message[] buffer;
	int prod, cons;
	int m_tot, m_got;

	public ProdConsBuffer(int tailleBuffer, TestProdCons test) {
		buffer = new Message[tailleBuffer];
		prod = 0;
		cons = 0;
		m_tot = 0;
		m_got = 0;
		tester = test;
	}

	@Override
	public synchronized void put(Message m) {
		while (!(nmsg() < buffer.length)) {
			try { wait();}
			catch(InterruptedException e) {}
		}
		buffer[prod] = m;
		prod++;
		prod = prod % buffer.length;
		m_tot++;
		notifyAll();
	}

	@Override
	public synchronized Message get() {
		while(!(nmsg() > 0)) {
			try { wait();}
			catch(InterruptedException e) {}
		}
		Message m = buffer[cons];
		buffer[cons] = null;	
		cons++;
		cons = cons % buffer.length;
		m_got++;
		System.out.println("nbConsumed : " + m_got);
		notifyAll();
		return m;
	}

	@Override
	public int nmsg() {
		return m_tot - m_got;
	}

	@Override
	public int totmsg() {
		return m_tot;
	}
	
}