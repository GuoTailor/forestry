package org.gyh.forestry;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * create by GYH on 2024/6/4
 */
public class MyDelayed implements Delayed {
    final long time;
    final String name;

    public MyDelayed(long milliseconds, String name) {
        this.time = System.currentTimeMillis() + milliseconds;
        this.name = name;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        System.out.println(System.currentTimeMillis() + " " + name + ">>");
        return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        System.out.println(System.currentTimeMillis() + " " + name + "<<");
        if (other == this) { // compare zero ONLY if same object
            return 0;
        }
        if (other instanceof MyDelayed) {
            return (int) (time - ((MyDelayed) other).time);
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) -
                other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
