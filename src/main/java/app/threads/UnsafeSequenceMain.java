package app.threads;

public class UnsafeSequenceMain {
    public static void main(String[] args) {

        UnsafeSequence unsafeSequence = new UnsafeSequence();

        UnsafeSeqThread one = new UnsafeSeqThread();
        one.setUnsafeSequence(unsafeSequence);
        one.setName("one");

        UnsafeSeqThread two = new UnsafeSeqThread();
        two.setUnsafeSequence(unsafeSequence);
        two.setName("two");

        UnsafeSeqThread three = new UnsafeSeqThread();
        three.setUnsafeSequence(unsafeSequence);
        three.setName("three");

        UnsafeSeqThread four = new UnsafeSeqThread();
        four.setUnsafeSequence(unsafeSequence);
        four.setName("four");

        UnsafeSeqThread five = new UnsafeSeqThread();
        five.setUnsafeSequence(unsafeSequence);
        five.setName("five");

        UnsafeSeqThread six = new UnsafeSeqThread();
        six.setUnsafeSequence(unsafeSequence);
        six.setName("six");

        UnsafeSeqThread seven = new UnsafeSeqThread();
        seven.setUnsafeSequence(unsafeSequence);
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
