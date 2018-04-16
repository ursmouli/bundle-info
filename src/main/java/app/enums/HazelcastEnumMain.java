package app.enums;

import app.enums.HazelcastMode;

public class HazelcastEnumMain {
    public static void main(String[] args) {
        new HazelcastEnumMain().go();
    }

    public void go() {
        HazelcastMode mode = getHazelcastMode();

        if (mode != null) {
            System.out.println(mode.getValue());
        }
    }

    public HazelcastMode getHazelcastMode() {
        String actual = "CS";

        HazelcastMode retMode = null;
        for (HazelcastMode mode : HazelcastMode.values()) {
            if (mode.getValue().equals(actual)) {
                retMode = mode;
            }
        }

        return retMode;
    }
}
