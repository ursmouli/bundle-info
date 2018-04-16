package app.arrlist;

import app.lambda.TestModel;
import app.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ListToArray {
    public static void main(String[] args) {
        List<TestModel> modelList = new ArrayList<>();
        modelList.add(new TestModel("one", "onea"));
        modelList.add(new TestModel("two", "twoa"));
        modelList.add(new TestModel("three", "threea"));

        TestModel[] modelArr = new TestModel[modelList.size()];
        modelList.toArray(modelArr);

        for (TestModel model : modelArr) {
            System.out.println(model.getMessage());
        }

        // Java 8 forEach test
        List<Person> personList = new ArrayList<>();
        // personList.add(new Person("p1", 10));
        // personList.add(new Person("p2", 10));
        List<Person> newPersonList = new ArrayList<>();
        personList.forEach(person -> {
            newPersonList.add(person);
        });

        System.out.println("newPersonList: " + newPersonList.size());
    }
}
