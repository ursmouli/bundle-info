package app.lambda;

import app.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListLambdaTest {

    public static List<Person> PERSON_LIST = new ArrayList<>();
    public static int P_INDEX = 0;

    static {
        int age = 1;
        for (int i = 1; i <= 10_000_000; i++) {
            Person person = new Person();
            person.setName("person" + i);
            person.setAge(age++);
            PERSON_LIST.add(person);
        }
        P_INDEX = (PERSON_LIST.size() / 2);
    }

    public void streamTest() {
        Person person = PERSON_LIST.get(P_INDEX);

        if (PERSON_LIST.stream().anyMatch(person::equals)) {
            System.out.println("Stream - Found Person : " + person.getName());
        }
    }

    public void parallelStreamTest() {
        Person person = PERSON_LIST.get(P_INDEX);

        if (PERSON_LIST.parallelStream().anyMatch(person::equals)) {
            System.out.println("ParallelStream - Found Person : " + person.getName());
        }
    }

    public void withoutStreamTest() {
        long startTime = System.nanoTime();
        Person person = new Person();
        person.setName("Mouli");
        person.setAge(30);

        if (PERSON_LIST.contains(person)) {
            System.out.println("contains Mouli in person list");
        }
        /*for (Person tmpPerson : PERSON_LIST) {
            if (tmpPerson.equals(person)) {
                System.out.println("contains Mouli in person list");
            }
        }*/
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("without Stream  : " + TimeUnit.NANOSECONDS.toNanos(totalTime));
    }

    public void streamForEachTest() {
        long startTime = System.nanoTime();
        Person person = new Person();
        person.setName("Mouli");
        person.setAge(30);

        if (PERSON_LIST.stream().anyMatch(person::equals)) {
            System.out.println("contains Mouli in person list");
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("stream          : " + TimeUnit.NANOSECONDS.toNanos(totalTime));
    }

    public void parallelStreamForEachTest() {
        long startTime = System.nanoTime();
        Person person = new Person();
        person.setName("Mouli");
        person.setAge(30);
        if (PERSON_LIST.parallelStream().anyMatch(person::equals)) {
            System.out.println("contains Mouli in person list");
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("parallelStream  : " + TimeUnit.NANOSECONDS.toNanos(totalTime));
    }

    // personList.stream().noneMatch(person -> Objects.equals(person, person3)
    public static void main(String[] args) {
        ListLambdaTest listLambdaTest = new ListLambdaTest();
        for (int i = 0; i < 10; i++) {
            System.out.println("-----------------" + i + "------------------");

            // listLambdaTest.streamTest();
            listLambdaTest.streamForEachTest();

            // listLambdaTest.parallelStreamTest();
            listLambdaTest.parallelStreamForEachTest();

            listLambdaTest.withoutStreamTest();
        }
    }
}
