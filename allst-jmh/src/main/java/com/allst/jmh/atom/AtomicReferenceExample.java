package com.allst.jmh.atom;

import com.allst.jmh.entity.DebitCard;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 模拟多线程下对账户+10操作
 *
 * @author Hutu
 * @since 2024-04-19 下午 09:44
 */
public class AtomicReferenceExample {
    static volatile DebitCard debitCard = new DebitCard("KangKang", 1000);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread("T_" + i) {
                @Override
                public void run() {
                    while (true) {
                        final DebitCard dc = debitCard;
                        DebitCard newDc = new DebitCard(dc.getAccount(), dc.getAmount() + 10);
                        System.out.println(newDc);
                        debitCard = newDc;
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
