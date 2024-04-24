package com.allst.jmh.tools;

import java.util.concurrent.Phaser;

/**
 * @author Hutu
 * @since 2024-04-24 下午 10:41
 */
public class PhaserExample4 {
    public static void main(String[] args) throws InterruptedException {
        // 定义只有一个分片的Phaser
        Phaser root = new Phaser(1);
        // 对root Phaser进行断言
        assertState(root, 0, 1, 1);
        // root phaser调用arrive方法，使得root phaser进入下一个Phase（阶段）
        assert root.arrive() == 0;

        // 定义两个子Phaser，分片个数分别为1
        Phaser child1 = new Phaser(root, 1);
        Phaser child2 = new Phaser(root, 1);

        // root Phaser的注册分片达到了3个
        assertState(root, 1, 3, 3);
        // 子Phaser当前的Phase（阶段）编号与父Phaser的Phase（阶段）编号一致
        assertState(child1, 1, 1, 1);
        assertState(child2, 1, 1, 1);
    }

    // 断言方法
    private static void assertState(Phaser phaser, int phase, int partites, int unarrived) {
        assert phaser.getPhase() == phase;
        assert phaser.getRegisteredParties() == partites;
        assert phaser.getUnarrivedParties() == unarrived;
    }
}
