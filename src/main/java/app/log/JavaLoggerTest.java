package app.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLoggerTest {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s [%2$s] - %5$s%6$s%n");
    }

    private static final Logger LOGGER = Logger.getLogger(JavaLoggerTest.class.getName());

    public void go() {
        TestObject testObject = null;
        try {
            testObject.getName();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"exception message", e);
        }
    }
    public static void main(String[] args) {
        new JavaLoggerTest().go();
    }
}

class TestObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
