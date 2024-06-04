package org.gyh.forestry.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * create by GYH on 2024/6/4
 */
public class RetryEntity implements Delayed {
    final long time;
    final int req;
    final int retryCount;
    final WebSocketSession session;
    final TextMessage textMessage;

    public RetryEntity(long milliseconds, int req, int retryCount, WebSocketSession session, TextMessage textMessage) {
        this.time = System.currentTimeMillis() + milliseconds;
        this.req = req;
        this.retryCount = retryCount;
        this.session = session;
        this.textMessage = textMessage;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (other == this) { // compare zero ONLY if same object
            return 0;
        }
        if (other instanceof RetryEntity) {
            return (int) (time - ((RetryEntity) other).time);
        }
        long d = (getDelay(TimeUnit.NANOSECONDS) -
                other.getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
