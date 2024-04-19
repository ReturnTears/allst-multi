package com.allst.jmh.atom;

import com.allst.jmh.entity.DebitCard;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * AtomicReference的应用场景
 * 使用AtomicReference封装DebitCard的对象引用，每一次对AtomicReference的更新操作，都采用CAS这一乐观非阻塞的方式进行。
 * CAS算法在此处就是要确保接下来要修改的对象引用是基于当前线程刚才获取的对象引用，否则更新将直接失败。
 *
 * @author Hutu
 * @since 2024-04-19 下午 09:44
 */
public class AtomicReferenceExample2 {
    private static AtomicReference<DebitCard> debitCardRef = new AtomicReference<>(new DebitCard("KangKang", 1000));

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread("T_" + i) {
                @Override
                public void run() {
                    while (true) {
                        final DebitCard dc = debitCardRef.get();
                        DebitCard newDc = new DebitCard(dc.getAccount(), dc.getAmount() + 10);
                        if (debitCardRef.compareAndSet(dc, newDc)) {
                            System.out.println(newDc);
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(20));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
