package com.allst.jmh.atom;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Hutu
 * @since 2024-05-24 下午 10:12
 */
public class DelayedEntity implements Delayed {
    // 元素数据内容
    @lombok.Getter
    private final String value;
    // 用于计算失效时间
    private final long time;

    public DelayedEntity(String value, long delayTime) {
        this.value = value;
        // 该元素可在（当前时间+delayTime）毫秒后消费，也就是说延迟消费delayTime毫秒
        this.time = delayTime + System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "DelayedEntity{" +
                "value='" + value + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long delta = time - System.currentTimeMillis();
        return unit.convert(delta, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.time < ((DelayedEntity) o).time) {
            return -1;
        } else if (this.time > ((DelayedEntity) o).time) {
            return 1;
        } else
            return 0;
    }
}
