package app.files

import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.CloseShieldInputStream
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*
import static org.hamcrest.Matchers.*

class InputStreamReadWriteTest {

    def fileName = "sample.txt"
    byte[] iStreamByteArr;

    InputStream iStream
    InputStream tmpInputStream

    @Before
    void before() throws Exception {
        iStream = InputStreamReadWriteTest.class.getClassLoader().getResourceAsStream(fileName)
        iStreamByteArr = IOUtils.toByteArray(iStream)
    }

    @After
    void after() throws Exception {
        if (iStream != null) {
            iStream.close()
        }
        iStreamByteArr = null;
    }

    /**
     * It is to confirm that InputStream is available before processing/reading InputStream.
     */
    @Test
    void "test InputStream is available"() {
        tmpInputStream = getTmpInputStream(iStreamByteArr)
        assertThat(tmpInputStream.available(), is(greaterThan(0)))
        tmpInputStream.close()
    }

    /**
     * It is to confirm that after processing/reading bytes the
     * InputStream available count is zero.
     */
    @Test
    void "test InputStream is not available after all contents are read"() {
        tmpInputStream = getTmpInputStream(iStreamByteArr)
        IOUtils.toByteArray(tmpInputStream)
        assertThat(tmpInputStream.available(), is(equalTo(0)))
        tmpInputStream.close()
    }

    /**
     * It is to confirm that after reading bytes from InputStream the data
     * available count decreases and count is less than to the original bytesAvailable.
     */
    @Test
    void "test iStream is not available after few contents are read"() {
        tmpInputStream = getTmpInputStream(iStreamByteArr)
        int bytesAvailable = tmpInputStream.available()
        int count = 0
        byte[] buffer = new byte[5]
        while (count < 10) {
            tmpInputStream.read(buffer)
            count++
        }
        println("before : " + bytesAvailable + ", after : " + tmpInputStream.available())
        assertThat(bytesAvailable, is(not(equalTo(tmpInputStream.available()))))
        assertThat(tmpInputStream.available(), is(lessThan(bytesAvailable)))
        tmpInputStream.close()
    }

    /**
     * It is to confirm that CloseShieldInputStream also behaves the same in respect to reading/processing bytes
     * of InputStream. The CloseShieldInputStream ensures that actual stream passes will not get closed.
     */
    @Test
    void "test InputStream not available with CloseShieldInputStream implementation"() {
        tmpInputStream = getTmpInputStream(iStreamByteArr)
        def closeShieldInputStream = new CloseShieldInputStream(tmpInputStream)
        IOUtils.readLines(closeShieldInputStream)
        assertThat(tmpInputStream.available(), is(equalTo(0)))
        closeShieldInputStream.close()
        tmpInputStream.close()
    }

    @Test
    void "test InputStream available after passing ByteArrayInputStream to BufferedInputStream"() {
        def bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(iStreamByteArr))
        assertThat(bufferedInputStream.available(), is(greaterThan(0)))

    }

    @Test
    void "test file content is equal to stream content"() {
        tmpInputStream = getTmpInputStream(iStreamByteArr)
        def available = tmpInputStream.available()
        File tempFile = File.createTempFile("notesAVTmp", "_")
        org.apache.wicket.util.file.File wicketFile = new org.apache.wicket.util.file.File(tempFile)
        wicketFile.write(tmpInputStream)

        def fileLen = (int) tempFile.length()
        assertThat(available, is(equalTo(fileLen)))
    }

    static InputStream getTmpInputStream(byte[] iSteamByteArr) {
        return new ByteArrayInputStream(iSteamByteArr)
    }
}
