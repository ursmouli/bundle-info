package app.genric;

import app.model.Student;
import app.model.Address;

public class TestInterfaceImpl implements TestInterface<Object, Student> {
    @Override
    public Object build(String name) {
        return null;
    }
}
