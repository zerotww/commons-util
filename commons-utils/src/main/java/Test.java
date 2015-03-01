import org.apache.log4j.Logger;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.fatal("fatal");
	}

}
