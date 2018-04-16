package app.ser;

import app.model.Address;
import app.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerMain.class);

    public static void main(String[] args) {
        SerMain ser = new SerMain();

        int age = 10;
        age = age >> 2;

        System.out.println("age" + age);

        age = age << 2;
        System.out.println("age" + age);

        //ser.serializeObject();
        ser.serializeExtendedObject();
    }

    public void serializeExtendedObject() {
        Student student = new Student();
        student.setName("std1");
        student.setAge(10);
        student.setGrade("1");
        student.setSection("A");
        student.setScore(100);

        student.setObject(new Object());

        Address address = new Address();
        address.setCity("snd");

        student.setAddress(address);

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        String fileName = "C:\\workspace\\work\\ser.txt";

        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(student);
            oos.flush();

            System.out.println("success");

            ois = new ObjectInputStream(new FileInputStream(fileName));
            Student retStd = (Student) ois.readObject();

            System.out.println(retStd.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void serializeObject() {
        Person person = new Person("mouli", 30);

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("C:\\workspace\\work\\ser.txt");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(person);
            oos.flush();

            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
