package prodcons.v3;

import java.util.concurrent.Semaphore;


public class ProdConsBuffer implements IProdConsBuffer {

	Message[] buffer;
	Semaphore sem;
	Semaphore get;
	int prod, cons;
	int m_tot, m_got;
	
	public ProdConsBuffer(int buffSize) {

		buffer = new Message[buffSize];
		sem = new Semaphore(buffSize);
		get = new Semaphore(0);
		m_tot = 0;
		m_got = 0;
		prod = 0;
		cons = 0;

	}

	@Override
	public void put(Message m) throws InterruptedException {

		sem.acquire();
		try {
			buffer[prod] = m;
			incrProd();
		} finally {
			get.release();
		}

	}

	@Override
	public Message get() throws InterruptedException {
		get.acquire();
		Message m = buffer[cons];
		try {
			if (nmsg() > 0) {
				incrCons();
				buffer[cons] = null; // Permet de tester si les wait fonctionnent dans consume
			}
		} finally {
			sem.release();
			}
		return m;

	}
	
	@Override
	public Message[] get(int k) throws InterruptedException {
		Message [] res = new Message[k];
		for (int i = 0; i < k; i++) {
			res[i] = get();
		}
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
