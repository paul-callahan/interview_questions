package com.redacted.answers.paulcallahan;

/**
 * Created by paul on 11/8/16.
 */
public final class EnterExit implements Comparable<EnterExit> {
    final long enter;
    final long exit;

    public EnterExit(long enter, long exit) {
        if (enter <= exit) {
            this.enter = enter;
            this.exit = exit;
        } else throw new RuntimeException("enter must be before exit");
    }

    final boolean contains(long unixTime) {
        return (enter <= unixTime) && (unixTime <= exit);
    }

    public final int compareTo(EnterExit that) {
        if (this.enter < that.enter) return -1;
        else if (this.enter > that.enter) return +1;
        else if (this.exit < that.exit) return -1;
        else if (this.exit > that.exit) return +1;
        else return 0;
    }
}
