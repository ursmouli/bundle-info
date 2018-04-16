package app.threads;

public class SafeSequenceMain {
    public static void main(String[] args) {

        SafeSequence safeSequence = new SafeSequence();

        SafeSeqThread one = new SafeSeqThread();
        one.setSafeSequence(safeSequence);
        one.setName("one");

        SafeSeqThread two = new SafeSeqThread();
        two.setSafeSequence(safeSequence);
        two.setName("two");

        SafeSeqThread three = new SafeSeqThread();
        three.setSafeSequence(safeSequence);
        three.setName("three");

        SafeSeqThread four = new SafeSeqThread();
        four.setSafeSequence(safeSequence);
        four.setName("four");

        SafeSeqThread five = new SafeSeqThread();
        five.setSafeSequence(safeSequence);
        five.setName("five");

        SafeSeqThread six = new SafeSeqThread();
        six.setSafeSequence(safeSequence);
        six.setName("six");

        SafeSeqThread seven = new SafeSeqThread();
        seven.setSafeSequence(safeSequence);
        seven.setName("seven");

        Thread oneThread = new Thread(one);
        Thread twoThread = new Thread(two);
        Thread threeThread = new Thread(three);
        Thread fourThread = new Thread(four);
        Thread fiveThread = new Thread(five);
        Thread sixThread = new Thread(six);
        Thread sevenThread = new Thread(seven);

        oneThread.start();
        twoThread.start();
        threeThread.start();
        fourThread.start();
        fiveThread.start();
        sixThread.start();
        sevenThread.start();
    }
}
