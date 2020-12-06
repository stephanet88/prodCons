package prodcons.v1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

public class TestProdCons {

	ProdConsBuffer buff;
	int nbMessage;
	Producer[] producers;
	Consumer[] consumers;
	
	public TestProdCons(int szBuffer, int nProd, int minProd, int maxProd, int prodTime, int nCons, int consTime) throws InterruptedException {
		buff = new ProdConsBuffer(szBuffer, this);
		
		List<Integer> run = new ArrayList<Integer>(nProd + nCons);
		
		producers = new Producer[nProd];
		for (int i = 0; i < nProd; i++) {
			producers[i] = new Producer(minProd, maxProd, prodTime, buff);
			nbMessage += producers[i].getNbMessage();
			run.add(i);
		}
		System.out.println("nbMessage : "+ nbMessage);
		
		consumers = new Consumer[nCons];
		for(int i = 0; i < nCons; i++) {
			consumers[i] = new Consumer(consTime, buff);
			run.add(i + nProd);
		}
		
		Collections.shuffle(run);
		
		for (int i = 0; i < run.size(); i++) {
//			System.out.println(run.get(i));
			if(run.get(i) < nProd) {
				producers[run.get(i)].start();
			}
			else 
				consumers[run.get(i) - nProd].start();
		}
		
		Thread.yield();
		
		for (int i = 0; i < nProd + nCons; i++) {
			if(i < nProd)
				producers[i].join();
			else 
				consumers[i - nProd].join();
		}
		
	}
	
	public boolean doStop() {
		return (nbMessage == buff.getNbConsumed());
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException, InterruptedException {
		
		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getResourceAsStream("options.xml"));
		
		int nProd = Integer.parseInt(properties.getProperty("nProd"));
		int minProd = Integer.parseInt(properties.getProperty("minProd"));
		int maxProd = Integer.parseInt(properties.getProperty("maxProd"));
		int prodTime = Integer.parseInt(properties.getProperty("prodTime"));
		
		int nCons = Integer.parseInt(properties.getProperty("nCons"));
		int consTime = Integer.parseInt(properties.getProperty("consTime"));
		
		int sizeBuffer = Integer.parseInt(properties.getProperty("bufSz"));
		
		TestProdCons tests = new TestProdCons(sizeBuffer, nProd, minProd, maxProd, prodTime, nCons, consTime);
		
	}

	public void stopAll() {
		for (int i = 0; i < producers.length; i++) {
			producers[i].stop();
		}
		System.out.println("That's all!");
	}
	
}
