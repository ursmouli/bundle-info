package app.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class SLF4JLogging {
    private static final Logger LGR = LoggerFactory.getLogger(SLF4JLogging.class);

    public static void main(String[] args) {
        final String name = "Mouli";
        LGR.debug("String {} name ", name);
        /*try {
            throw new FileNotFoundException("File not found");
        } catch (FileNotFoundException e) {
            printLog(e.getMessage(), e);
        }*/

    }

    public static void printLog(final String message, final Throwable t) {
        LGR.error("Exception: {}", message, t);
    }
}
