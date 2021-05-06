/*
 * Decompiled with CFR 0.150.
 */
package dev.client.velocity.util.client;

public class Timer {
    private long time = -1L;

    public long getTime(long time) {
        return time / 1000000L;
    }

    public boolean passed(long ms) {
        return this.getMs(System.nanoTime() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getMs(long time) {
        return time / 1000000L;
    }

    public boolean sleep(long time) {
        if (this.time() >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }
}

