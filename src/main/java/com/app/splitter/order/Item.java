package com.app.splitter.order;

import jakarta.persistence.*;

@Entity
@Table(name="order_items")
public class Item {
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name="id")
    private String id;

    @Column(name="orderId")
    private long orderId;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private float price;
    @Column(name="quantity")
    private int quantity;


    public Item() {
    }

    public Item(long orderId, String name, float price, int quantity) {
        this.id = String.valueOf(orderId)+Math.abs(name.hashCode());
        this.orderId = orderId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
