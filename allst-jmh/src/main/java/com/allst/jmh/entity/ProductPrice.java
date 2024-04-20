package com.allst.jmh.entity;

/**
 * 商品价格实体
 *
 * @author Hutu
 * @since 2024-04-20 上午 11:07
 */
public class ProductPrice {
    private final int prodID;
    private double price;

    public ProductPrice(int prodID) {
        this(prodID, -1);
    }

    public ProductPrice(int prodID, double price) {
        this.prodID = prodID;
        this.price = price;
    }

    public int getProdID() {
        return prodID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "prodID=" + prodID +
                ", price=" + price +
                '}';
    }
}
