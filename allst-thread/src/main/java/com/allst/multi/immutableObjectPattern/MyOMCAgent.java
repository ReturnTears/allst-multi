package com.allst.multi.immutableObjectPattern;

/**
 * @author Hutu
 * @since 2023-02-15 下午 10:53
 */
public class MyOMCAgent extends Thread {
    @Override
    public void run() {
        boolean isTableModificationMsg = false;
        String updatedTableName = null;
        while (true) {
            // 省略其他代码
            if (isTableModificationMsg) {
                if ("MyMMSCInfo".equals(updatedTableName)) {
                    MyMMSCRouter.setInstance(new MyMMSCRouter());
                }
            }
            // 省略其他代码
        }
    }
}
