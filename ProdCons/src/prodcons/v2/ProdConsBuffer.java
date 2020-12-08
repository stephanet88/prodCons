package prodcons.v2;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {

	Message[] buffer;
	Semaphore put;
	Semaphore sem, get;
	int prod, cons;
	int m_tot, m_got;

	public ProdConsBuffer(int buffSize) {

		buffer = new Message[buffSize];

		put = new Semaphore(1);
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
		put.acquire();
		try {
			m_tot++;
			buffer[prod] = m;
			prod = (prod + 1) % buffer.length;
		} finally {
			put.release();
			get.release();
		}

	}

	@Override
	public Message get() throws InterruptedException {
		get.acquire();
		put.acquire();
		Message m = buffer[cons];
		try {
			if (nmsg() > 0) {
				cons = (cons + 1) % buffer.length;
				m_got++;
				buffer[cons] = null;
			}
//		System.out.println(m);
		} finally {
			put.release();
			sem.release();
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
