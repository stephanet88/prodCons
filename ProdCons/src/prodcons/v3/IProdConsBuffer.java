package prodcons.v3;

public interface IProdConsBuffer {
	
//	String[] buffer;
		
	/** Put the message m in the prodcons buffer**/
	public void put(Message m) throws InterruptedException;
	
	/** Retrieve a message from the prodcons buffer, 
	 * following a FIFOorder(if M1 was put before M2, M&
	 * will be get before M2)**/
	public Message get() throws InterruptedException;
	
	/** Returns the number of messages currently available in 
	 * theprod-cons buffer **/
	public int nmsg();
	
	/** Returns the total number of messages that have 
	 * been put in the buffersince its creation **/
	public int totmsg();

}
