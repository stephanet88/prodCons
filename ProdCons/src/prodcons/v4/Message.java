package prodcons.v4;

import java.util.concurrent.Semaphore;

public class Message {

	String message;
	int nbExamplaire;
	Semaphore sem;
	int nbAcq;
	
	public Message(String m) {
		message = m;
		nbExamplaire = 1;
	}
	
	public Message(String m, int n) {
		message = m;
		nbExamplaire = n;
		sem = new Semaphore(0);
		nbAcq = 0;
	}
	
	public String toString() {
		return message;
	}
	
	public void setEx(int n) {
		nbExamplaire = n;
	}
	
	public void addNbAcq() {
		nbAcq ++;
	}
	
	public int getNbAcq() {
		return nbAcq;
	}
	
}