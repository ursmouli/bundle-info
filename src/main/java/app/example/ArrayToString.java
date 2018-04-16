package app.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToString {

    String[] one = new String[] { "cp1", "cpv1", "..." };
    String[] two = new String[] { "cp2", "cpv2", "..." };

    public void firstImpl() {
        System.out.println("--------- first impl --------------");
        List<String[]> strArrList = new ArrayList<>();
        strArrList.add(one);
        strArrList.add(two);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        List<String> valuesList = new ArrayList<>();
        for (String[] values : strArrList) {
            String[] toArr = new String[] { values[0], values[1]};
            valuesList.add(Arrays.toString(toArr).replaceAll(" ", "").replace(",", "="));
        }

        String[] valuesArr = new String[valuesList.size()];
        valuesList.toArray(valuesArr);

        sb.append(Arrays.toString(valuesArr).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", ""));

        sb.append("}");

        System.out.println(sb.toString());

        String newStr = sb.toString().substring(1, sb.toString().length() - 1);
        System.out.println("newStr : " + newStr);

        String[] newStrSplit = newStr.split(",");
        System.out.println("newStrSplit : " + Arrays.toString(newStrSplit));
    }

    public void secondImpl() {
        System.out.println("--------- second impl --------------");
        List<String[]> strArrList = new ArrayList<>();
        //strArrList.add(one);
        //strArrList.add(two);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        List<KeyValue> valuesListArr = new ArrayList<>();
        strArrList.forEach(item ->  valuesListArr.add(new KeyValue(item[0], item[1])));
        if (!valuesListArr.isEmpty()) {
            valuesListArr.forEach(itemArr -> sb.append(itemArr.toString()).append(","));
        }
        sb.append("}");

        System.out.println(sb.toString().replace(",}", "}").replace(".", ""));
    }

    public static void main(String[] args) {
        ArrayToString arrayToString = new ArrayToString();
        arrayToString.secondImpl();
    }
}
