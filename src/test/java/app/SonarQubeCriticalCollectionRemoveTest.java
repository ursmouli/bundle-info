package app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SonarQubeCriticalCollectionRemoveTest {

    List<Person> personListOne;
    List<Person> personListTwo;
    List<String> namesList;

    @Before
    public void before() {
        personListOne = new ArrayList<>();
        personListTwo = new ArrayList<>();
        namesList = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            personListOne.add(new Person("person" + i, i));
            personListTwo.add(new Person("person" + i, i));
            namesList.add("name" + i);
        }
    }

    @Test
    public void testJava8RemoveIf() {
        long startTime = System.nanoTime();
        personListOne.removeIf(person -> person.getAge() > 120);
        long java8endTime = getEndNanoTime(startTime);

        startTime = System.nanoTime();
        for (Iterator<Person> iterator = personListTwo.iterator(); iterator.hasNext();) {
            Person person = iterator.next();
            if (person.getAge() > 120) {
                iterator.remove();
            }
        }
        long javaEndTime = getEndNanoTime(startTime);

        assertTrue("Normal Java remove operation time should be greater then Java8 new removeIf", javaEndTime > java8endTime);
    }

    @Test
    public void testJavaListRemoveObjectWithoutCondition() {

        long startTime = System.nanoTime();
        personListOne.removeIf(getPersonToRemove()::equals);
        long java8EndTime = getEndNanoTime(startTime);

        startTime = System.nanoTime();
        personListOne.remove(getPersonToRemove());
        long javaEndTime = getEndNanoTime(startTime);

        assertTrue("Java-EndTime should be < Java8-EndTime", javaEndTime < java8EndTime);

        int javaWinCount = 0;
        int java8WinCount = 0;
        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            personListOne.remove(getPersonToRemove());
            javaEndTime = getEndNanoTime(startTime);

            startTime = System.nanoTime();
            personListOne.removeIf(getPersonToRemove()::equals);
            java8EndTime = getEndNanoTime(startTime);

            if (javaEndTime > java8EndTime) {
                java8WinCount++;
            } else {
                javaWinCount++;
            }
        }

        assertTrue("Java-EndTime should be > Java8-EndTime", javaWinCount > java8WinCount);
    }

    @Test
    public void testJavaListRemoveStringWithoutCondition() {
        long startTime = System.nanoTime();
        namesList.removeIf(getStringToRemove()::equals);
        long java8EndTime = getEndNanoTime(startTime);

        startTime = System.nanoTime();
        namesList.remove(getStringToRemove());
        long javaEndTime = getEndNanoTime(startTime);

        assertTrue("Java-EndTime should be < Java8-EndTime", javaEndTime < java8EndTime);

        int javaWinCount = 0;
        int java8WinCount = 0;

        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            namesList.remove(getStringToRemove());
            javaEndTime = getEndNanoTime(startTime);

            startTime = System.nanoTime();
            namesList.removeIf(getStringToRemove()::equals);
            java8EndTime = getEndNanoTime(startTime);

            if (javaEndTime > java8EndTime) {
                java8WinCount++;
            } else {
                javaWinCount++;
            }
        }

        assertTrue("Java-EndTime should be > Java8-EndTime", javaWinCount > java8WinCount);
    }

    private long getEndNanoTime(long startTime) {
        return (System.nanoTime() - startTime);
    }

    private Person getPersonToRemove() {
        return personListOne.get(personListOne.size()/2 + 10_000);
    }

    private String getStringToRemove() {
        return namesList.get(namesList.size()/2 + 10_000);
    }
}

class Person {
    private String name;
    private Integer age;
    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
}
