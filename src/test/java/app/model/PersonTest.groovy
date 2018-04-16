package app.model

import spock.lang.Specification

class PersonTest extends Specification {
    Person person
    def setup() {
        person = new Person() {
            String personName = "New Name"
            @Override
            String getName() {
                return personName
            }
        }
    }

    def "test person name"() {
        when:
        String name = person.getName()

        Set<Person> persons = getPersonSet()
        persons.each {
            println it.name
        }

        then:
        name == "New Name"
    }

    def getPersonSet() {
        Set<Person> persons = []
        persons.add(new Person(name: "Set Name"))
        return persons
    }
}
