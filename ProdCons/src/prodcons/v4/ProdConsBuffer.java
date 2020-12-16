package prodcons.v4;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] buffer;
	int prod, cons;
	int m_tot, m_got;
	
	public ProdConsBuffer(int buffSize) {

		buffer = new Message[buffSize];
		m_tot = 0;
		m_got = 0;
		prod = 0;
		cons = 0;

	}

	@Override
	public synchronized void put(Message m) throws InterruptedException {

		while (!(nmsg() < buffer.length)) {
			try { wait();}
			catch(InterruptedException e) {}
		}
		buffer[prod] = m;
		incrProd();
		m_tot++;
		notifyAll();

	}
	
	@Override
	public synchronized void put(Message m, int n) throws InterruptedException {
		
		while(!(nmsg() < buffer.length))
			wait();
		buffer[prod] = m;
		m.addNbAcq();
		incrProd();
		m_tot++;
		notifyAll();
		while(m.nbExamplaire > 0)
			wait();


	}

	@Override
	public synchronized Message get() throws InterruptedException {
		
		while(nmsg() <= 0)
			wait();
		Message m = buffer[cons];
		m.nbExamplaire--;
		
		while(m.nbExamplaire > 0)
			wait();
		
		if(m.nbExamplaire-- == 0) {
			incrCons();
			notifyAll();
			m_got++;
		}
		//notifyAll();
		return m;

	}
	
	@Override
	public Message[] get(int k) throws InterruptedException {
		Message [] res = new Message[k];
		return res;
	}

	@Override
	public int nmsg() {
		return totmsg() - m_got;
	}

	@Override
	public int totmsg() {
		return m_tot;
	}

	public Message[] getMessageBuffer() {
		return buffer;
	}
	
	public synchronized void incrCons() {
		cons = (cons + 1) % buffer.length;
	}
	
	public synchronized void incrProd() {
		prod = (prod + 1) % buffer.length;
	}

}
