package app.files;

import app.model.Person;
import app.service.IPersonService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class MockVerifyTest {

    private Person person;

    private IPersonService personService;

    @Before
    public void setup() {
        person = new Person();
        personService = mock(IPersonService.class);
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = new Person("p1", 28);
        doThrow(Exception.class).when(personService).updatePerson(any(Person.class));

        // verify(personService, times(0)).updatePerson(null);
        verify(personService, never()).updatePerson(person);
    }
}
