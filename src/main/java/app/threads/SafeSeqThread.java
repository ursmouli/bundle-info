package app.threads;

public class SafeSeqThread implements Runnable {
    private SafeSequence safeSequence;
    private String name;

    @Override
    public void run() {
        System.out.println(name + " - " + safeSequence.getValue());
    }

    public void setSafeSequence(SafeSequence safeSequence) {
        this.safeSequence = safeSequence;
    }

    public void setName(String name) {
        this.name = name;
    }
}
