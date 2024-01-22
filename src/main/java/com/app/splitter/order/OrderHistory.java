package com.app.splitter.order;

import jakarta.persistence.*;

@Entity
@Table(name="order_history")
public class OrderHistory {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="order_id")
    private int orderId;
    @Column(name="person_id")
    private int personId;
    @Column(name="item_name")
    private String itemName;
    @Column(name="item_price")
    private float item_price;
    @Column(name="item_qty")
    private float item_qty;

    public OrderHistory() {
    }

    public OrderHistory(int order_id, int personId, String itemName, float item_price, float item_qty) {
        this.orderId = order_id;
        this.personId = personId;
        this.itemName = itemName;
        this.id = String.valueOf(order_id)+String.valueOf(personId)+Math.abs(itemName.hashCode());
        this.item_price = item_price;
        this.item_qty = item_qty;
    }

    public OrderHistory(String id, int order_id, int personId, String itemName, float item_price, float item_qty) {
        this.id = id;
        this.orderId = order_id;
        this.personId = personId;
        this.itemName = itemName;
        this.item_price = item_price;
        this.item_qty = item_qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItem_price() {
        return item_price;
    }

    public void setItem_price(float item_price) {
        this.item_price = item_price;
    }

    public float getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(float item_qty) {
        this.item_qty = item_qty;
    }
}
