package com.allst.jmh.entity;

/**
 * @author Hutu
 * @since 2024-04-19 下午 09:42
 */
public class DebitCard {
    private final String account;
    private final int amount;

    public DebitCard(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "account='" + account + '\'' +
                ", amount=" + amount +
                '}';
    }
}
