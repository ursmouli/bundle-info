package app.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SafeSequence {

    @GuardedBy("this")
    private int value;

    public synchronized int getValue() {
        return value++;
    }
}
