package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] buffer;
	Semaphore sem;
	Semaphore get, getk;
	int prod, cons;
	int m_tot, m_got;
	
	public ProdConsBuffer(int buffSize) {

		buffer = new Message[buffSize];
		sem = new Semaphore(buffSize);
		get = new Semaphore(0);
		getk = new Semaphore(1);
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
		getk.acquire();
		Message m = null;
		try {
			m = doGet();
		} finally {
			getk.release();
			}
		return m;

	}
	
	@Override
	public Message[] get(int k) throws InterruptedException {
		getk.acquire();
		Message [] res = new Message[k];
		for (int i = 0; i < k; i++) {
			res[i] = doGet();
		}
		getk.release();
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
	
	public Message doGet() throws InterruptedException {
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
