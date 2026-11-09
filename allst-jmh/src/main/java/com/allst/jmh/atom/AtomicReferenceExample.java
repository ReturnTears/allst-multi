package com.allst.jmh.atom;

import com.allst.jmh.entity.DebitCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 模拟多线程下对账户+10操作
 *
 * @author Hutu
 * @since 2024-04-19 下午 09:44
 */
public class AtomicReferenceExample {
    static volatile DebitCard debitCard = new DebitCard("KangKang", 1000);

    static final AtomicReference<DebitCard> debitCard2 =
            new AtomicReference<>(new DebitCard("KangKang", 1000));

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            /*Thread t = new Thread("T_" + i) {
                @Override
                public void run() {
                    // 以下逻辑无法可靠得到线程执行后的完整结果：1、while(true)导致线程执行永不停止
                    // 2、volatile 仅保证可见性，不保证“读-改-写”复合操作的原子性，最终 debitCard.amount 会因竞态条件丢失更新。
                    while (true) {
                        final DebitCard dc = debitCard;
                        DebitCard newDc = new DebitCard(dc.getAccount(), dc.getAmount() + 10);
                        System.out.println(newDc);
                        debitCard = newDc;
                        try {
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(20));
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }


                }
            };*/

            Thread t = new Thread(() -> {
                for (int j = 0; j < 10; j++) { // 有限循环替代 while(true)
                    DebitCard oldDc, newDc;
                    do {
                        oldDc = debitCard2.get();
                        newDc = new DebitCard(oldDc.getAccount(), oldDc.getAmount() + 10);
                    } while (!debitCard2.compareAndSet(oldDc, newDc));
                }
            }, "T_" + i);
            threads.add(t);
            t.start();
        }
        // 阻塞等待全部完成
        for (Thread t : threads) {
            t.join();
        }
        // 此时读取的结果才是确定的
        System.out.println("final result : " + debitCard2.get().getAmount());
    }
}
