package app.example;

import app.model.Person;
import app.ser.Student;

import java.io.File;
import java.io.IOException;

public class TestClassExtends extends TestClass {

    @Override
    public Student addFile(File file) throws IOException {
        return new Student();
    }

    public static void main(String[] args) {

    }
}
