package prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] buffer;
	Semaphore sem;
	Semaphore put, get;
	int prod, cons;
	int m_tot, m_got;

	public ProdConsBuffer(int buffSize) {

		buffer = new Message[buffSize];
		sem = new Semaphore(1);
		put = new Semaphore(buffSize);
		get = new Semaphore(0);
		m_tot = 0;
		m_got = 0;
		prod = 0;
		cons = 0;

	}

	@Override
	public void put(Message m) throws InterruptedException {

		put.acquire();
		sem.acquire();
		try {
			m_tot++;
			buffer[prod] = m;
			prod = (prod + 1) % buffer.length;
		} finally {
			sem.release();
			get.release();
		}

	}

	@Override
	public Message get() throws InterruptedException {
		get.acquire();
		sem.acquire();
		Message m = buffer[cons];
		try {
			if (nmsg() > 0) {
				cons = (cons + 1) % buffer.length;
				m_got++;
				buffer[cons] = null;
			}
//		System.out.println(m);
		} finally {
			sem.release();
			put.release();
		}
		return m;

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
}
