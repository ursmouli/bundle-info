package app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SonarQubeCriticalCollectionContainsTest {

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
    public void test_ValidPerson_JavaContains_Vs_Java8AnyMatch() {
        Person person = getPersonToSearch();

        long startTime = System.nanoTime();
        personListOne.contains(person);
        long javaEndTime = getEndNanoTime(startTime);

        startTime = System.nanoTime();
        personListOne.parallelStream().anyMatch(person::equals);
        long java8EndTime = getEndNanoTime(startTime);

        assertTrue("Java-EndTime should be < Java8-EndTime", javaEndTime < java8EndTime);

        int javaWinCount = 0;
        int java8WinCount = 0;
        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            personListOne.contains(person);
            javaEndTime = getEndNanoTime(startTime);

            startTime = System.nanoTime();
            personListOne.parallelStream().anyMatch(person::equals);
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
    public void test_InValidPerson_JavaContains_Vs_Java8AnyMatch() {
        Person person = new Person("PersonName", 30);

        long startTime = System.nanoTime();
        personListOne.contains(person);
        long javaEndTime = getEndNanoTime(startTime);

        startTime = System.nanoTime();
        personListOne.parallelStream().anyMatch(person::equals);
        long java8EndTime = getEndNanoTime(startTime);

        assertTrue("Java-EndTime should be < Java8-EndTime", javaEndTime < java8EndTime);

        int javaWinCount = 0;
        int java8WinCount = 0;
        for (int i = 0; i < 10; i++) {
            startTime = System.nanoTime();
            personListOne.contains(person);
            javaEndTime = getEndNanoTime(startTime);

            startTime = System.nanoTime();
            personListOne.parallelStream().anyMatch(person::equals);
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

    private Person getPersonToSearch() {
        return personListOne.get(personListOne.size()/2 + 10_000);
    }

    private String getStringToSearch() {
        return namesList.get(namesList.size()/2 + 10_000);
    }

}
