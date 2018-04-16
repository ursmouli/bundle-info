package app.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogMain.class);
    public static void main(String[] args) {
        LOGGER.error("error log");
        LOGGER.warn("warn log");
        LOGGER.info("info log");
        LOGGER.debug("debug log");
        LOGGER.trace("trace log");

        String tenant = "TENANT";
        LOGGER.warn("All security are reset for tenant [{}]", tenant);
    }
}
