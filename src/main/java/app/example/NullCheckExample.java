package app.example;

import java.util.List;

public class NullCheckExample {

    private List<String> strList;

    public static void main(String[] args) {
        int curr = 1;
        for (int i = 0; i < 10; i++, curr++) {

        }
        System.out.println(curr);
    }
}
