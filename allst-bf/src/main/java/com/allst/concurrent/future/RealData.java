package com.allst.concurrent.future;

/**
 * @author Hutu
 * @since 2024-10-17 下午 09:34
 */
public class RealData implements Data {
    protected final String result;
    public RealData(String queryStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(queryStr);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = sb.toString();
    }
    @Override
    public String getResult() {
        return result;
    }
}
