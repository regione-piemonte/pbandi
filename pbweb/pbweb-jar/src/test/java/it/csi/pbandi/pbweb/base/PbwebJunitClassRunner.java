package it.csi.pbandi.pbweb.base;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

public class PbwebJunitClassRunner extends SpringJUnit4ClassRunner {

	public PbwebJunitClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	static {
		String log4jLocation = "src/test/java/it/csi/pbandi/pbweb/config/log4j.properties";
		try {
			Log4jConfigurer.initLogging(log4jLocation);
		} catch (FileNotFoundException ex) {
			System.err.println("Non riesco ad inizializzare il log4j alla location: " + log4jLocation);
		}
	}

}
