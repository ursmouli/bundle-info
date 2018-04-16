package app.service.impl;

import app.model.Person;
import app.service.IPersonService;

public class PersonService implements IPersonService {
    @Override
    public void updatePerson(Person person) throws Exception {
        if (person == null) {
            throw new Exception("Person cannot be null.");
        }
    }
}
