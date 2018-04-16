package app.manager;

import app.model.Person;
import app.service.IPersonService;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PersonManagerTest {
    private IPersonService personService;

    private PersonManager personManager;

    private Appender<ILoggingEvent> mockAppender;
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setup() {
        mockAppender = mock(Appender.class);
        captorLoggingEvent = new ArgumentCaptor<LoggingEvent>();
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);

        personService = mock(IPersonService.class);

        personManager = new PersonManager();
        personManager.setPersonService(personService);
    }

    @Test
    public void testUpdatePerson_Exception() throws Exception {
        Person p1 = new Person("p1", 10);
        doThrow(new Exception("")).when(personService).updatePerson(any());
        personManager.updatePersonInfo(p1);
        verify(mockAppender, atLeastOnce()).doAppend(captorLoggingEvent.capture());
        List<LoggingEvent> allValues = captorLoggingEvent.getAllValues();

        Matcher<LoggingEvent> expectedLogMessage = hasProperty("message", IsEqual.equalTo("Failed to update person"));
        assertThat(allValues, hasItem(expectedLogMessage));
    }
}
