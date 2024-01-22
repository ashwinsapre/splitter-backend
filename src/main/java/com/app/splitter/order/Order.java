package com.app.splitter.order;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Order(Long orderId, LocalDate date) {
        this.orderId = orderId;
        this.date = date;
    }

    public Order() {
    }
}
