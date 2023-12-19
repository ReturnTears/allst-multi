package com.allst.multi.basic;

/**
 * @author Hutu
 * @since 2023-12-19 下午 09:44
 */
public class ThreadCase7 {
    public static void main(String[] args) {
        SychCase7A case7A = new SychCase7A();
        SychCase7 case7 = new SychCase7(case7A);
        // Thread is executed
        case7.start();
    }
}
