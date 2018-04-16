package app.manager;

import app.model.Person;
import app.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonManager.class);

    private IPersonService personService;

    public void updatePersonInfo(Person person) {
        try {
            personService.updatePerson(person);
        } catch (Exception e) {
            LOGGER.error("some other");
            LOGGER.error("Failed to update person");
        }
    }

    public IPersonService getPersonService() {
        return personService;
    }

    public void setPersonService(IPersonService personService) {
        this.personService = personService;
    }
}
