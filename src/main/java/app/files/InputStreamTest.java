package app.files;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputStreamTest {

    public void go() throws Exception {
        String fileName = "test.txt";

        InputStream is1 = InputStreamTest.class.getClassLoader().getResourceAsStream(fileName);
        log("0 = " + is1.available());

        int count = 0;
        byte[] buffer = new byte[5];
        while(count < 11) {
            is1.read(buffer);
            count++;
        }

        log("0 - 1 = " + is1.available());

        byte[] isByteArr = IOUtils.toByteArray(is1);
        log("1 = " + is1.available());

        InputStream is2 = new ByteArrayInputStream(isByteArr);
        log("2 = " + is2.available());

        InputStream is3 = new ByteArrayInputStream(isByteArr);
        log("3 = " + is3.available());
    }


    private static void log(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) throws Exception {
        new InputStreamTest().go();
    }
}
