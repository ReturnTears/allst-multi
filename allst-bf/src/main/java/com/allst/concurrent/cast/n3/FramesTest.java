package com.allst.concurrent.cast.n3;

/**
 * 使用debug模式调试程序查看JVM栈帧的执行情况
 * @author YiYa
 * @since 2020-01-09 下午 11:51
 */
public class FramesTest {

    public static void main(String[] args) {
        mth1(22);
    }

    private static void mth1(int x) {
        int y = x + 1;
        Object m = mth2();
        System.out.println(m);
    }

    private static Object mth2() {
        Object n = new Object();
        return n;
    }

}
