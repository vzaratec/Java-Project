package phone.config;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadProperties extends Properties {
	private static final long serialVersionUID = 1L;

	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LoadProperties instance;
	private static final String FILE_PROPERTIES = "PhoneFormat.properties";

	public LoadProperties() {
		super();
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			super.load(classLoader.getResourceAsStream(FILE_PROPERTIES));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IOException => ", e);
		}
	}

	public static synchronized LoadProperties getInstance() {
		if (null == instance) {
			instance = new LoadProperties();
		}
		return instance;
	}

}
