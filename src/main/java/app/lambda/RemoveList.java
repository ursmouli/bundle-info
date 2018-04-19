package app.lambda;

import app.model.Person;

import javax.print.attribute.standard.MediaSize;
import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RemoveList {
    void testJava8ForRemove() {
        List<String> tmpStrList = new ArrayList<>();
        for (int i = 0; i < 100_000_00; i++) {
            tmpStrList.add("name-name" + i);
        }

        String item = tmpStrList.get(tmpStrList.size() - 1);
        long startTime = System.nanoTime();
        tmpStrList.removeIf(item::equals);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("java8-for    : " + TimeUnit.NANOSECONDS.toNanos(totalTime) + ", sec: " + seconds);
    }

    void testJavaForRemove() {
        List<String> tmpStrList = new ArrayList<>();
        for (int i = 0; i < 100_000_00; i++) {
            tmpStrList.add("name-name" + i);
        }

        String item = tmpStrList.get(tmpStrList.size() - 1);
        long startTime = System.nanoTime();
        boolean isContains = false;
        for (String str : tmpStrList) {
            if (str.equals(item)) {
                isContains = true;
                break;
            }
        }
        if (isContains) {
            tmpStrList.remove(item);
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("java-for    : " + TimeUnit.NANOSECONDS.toNanos(totalTime) + ", sec: " + seconds);
    }

    void testJavaConditioanlRemove() {
        List<Person> personList = new ArrayList<>();
        Person tmpPerson = null;
        int age = 0;
        for (int i = 0; i < 100_000; i++) {
            tmpPerson = new Person();
            tmpPerson.setName("person" + i);
            tmpPerson.setAge(age++);
            personList.add(tmpPerson);
        }

        long startTime = System.nanoTime();
        for (Iterator<Person> empIterator = personList.iterator();
            empIterator.hasNext();) {
            Person emp = empIterator.next();
            if (emp.getAge() > 30){
                empIterator.remove();
            }
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("java-for    : " + TimeUnit.NANOSECONDS.toNanos(totalTime) + ", sec: " + seconds);
    }

    void testJava8ConditioanlRemove() {
        List<Person> personList = new ArrayList<>();
        Person tmpPerson = null;
        int age = 0;
        for (int i = 0; i < 100_000; i++) {
            tmpPerson = new Person();
            tmpPerson.setName("person" + i);
            tmpPerson.setAge(age++);
            personList.add(tmpPerson);
        }

        long startTime = System.nanoTime();
        personList.removeIf(person -> person.getAge() > 120);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("java8-for    : " + TimeUnit.NANOSECONDS.toNanos(totalTime) + ", sec: " + seconds);
    }

    public static void main(String[] args) throws Exception {
        RemoveList removeList = new RemoveList();
        //removeList.testJava8();
        //removeList.testJava();

        /*removeList.testJavaForRemove();
        removeList.testJava8ForRemove();*/

        removeList.testJavaConditioanlRemove();
        removeList.testJava8ConditioanlRemove();
    }
}
