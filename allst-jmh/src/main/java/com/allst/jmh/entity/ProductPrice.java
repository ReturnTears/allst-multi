package com.allst.jmh.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品价格实体
 *
 * @author Hutu
 * @since 2024-04-20 上午 11:07
 */
public class ProductPrice {
    @Getter
    private final int prodID;
    @Setter
    private double price;

    public ProductPrice(int prodID) {
        this(prodID, -1);
    }

    public ProductPrice(int prodID, double price) {
        this.prodID = prodID;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPrice{prodID=" + prodID + ", price=" + price + '}';
    }
}
