package app.ser;

import app.model.Address;
import app.model.Person;

public class Student extends Person {
    private String grade;
    private String section;


    private int score;

    private Address address;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name=" + this.getName());
        sb.append(", age=" + this.getAge());
        sb.append(", score=" + this.getScore());
        sb.append(", obj=" + this.getObject());
        return sb.toString();
    }
}
