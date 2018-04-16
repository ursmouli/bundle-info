package app.threads;

public class UnsafeSeqThread implements Runnable {
    private UnsafeSequence unsafeSequence;
    private String name;

    @Override
    public void run() {
        System.out.println(name + " - " + unsafeSequence.getNext());
    }


    public void setUnsafeSequence(UnsafeSequence unsafeSequence) {
        this.unsafeSequence = unsafeSequence;
    }

    public void setName(String name) {
        this.name = name;
    }
}
