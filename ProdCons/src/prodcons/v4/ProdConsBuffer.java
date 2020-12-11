package prodcons.v4;

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
	public void put(Message m, int n) throws InterruptedException {

		sem.acquire();
		int p = prod;
		Semaphore sema = m.sem;
		try {
			buffer[prod] = m;
			m.addNbAcq();
			incrProd();
		} finally {
			get.release();
			if(sema != null) {
				sema.acquire();
			}
		}

	}

	@Override
	public Message get() throws InterruptedException {
		Message m = null;
		m = doGet();
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
//				System.out.println("doGet");
				int newNb = m.nbExamplaire - 1;
				Semaphore sema = m.sem;
				if(newNb == 0) {
					if(sema != null) {
//						System.out.println("waiting");
//						System.out.println(m.getNbAcq());
						for(int i = 0; i < m.getNbAcq(); i++) {
//							System.out.println(".");
							sema.release();
						}
					}
					buffer[cons] = null; 
					incrCons();
					sem.release();
				} else {
					buffer[cons].setEx(newNb);
//					System.out.println("nice : " + newNb);
					m.addNbAcq();
					get.release();
					sema.acquire();
				}
			}
		} finally {
		}
		return m;
	}

	public Message[] getMessageBuffer() {
		return buffer;
	}
	
	public synchronized void incrCons() {
		m_got++;
		cons = (cons + 1) % buffer.length;
	}
	
	public synchronized void incrProd() {
		m_tot++;
		prod = (prod + 1) % buffer.length;
	}

}
