package Test;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import prodcons.v1.*;

public class TestProdCons {

	ProdConsBuffer buff;
	
	public TestProdCons(int szBuffer) {
		buff = new ProdConsBuffer(szBuffer);
	}
	
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {
		
		Properties properties = new Properties();
		properties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("options.xml"));
		int nProd= Integer.parseInt(properties.getProperty("nProd"));
		int nCons= Integer.parseInt(properties.getProperty("nCons"));
		int consTime = Integer.parseInt(properties.getProperty("consTime"));
		int sizeBuffer = Integer.parseInt(properties.getProperty("bufSz"));
		
		TestProdCons tests = new TestProdCons(sizeBuffer);

		
	}
	
}
