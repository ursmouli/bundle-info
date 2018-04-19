package app.genric;

import app.model.Person;

public interface TestInterface<T, V extends Person> {

    T build(final String name);
}
