package com.app.splitter.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="person")
public class Person {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;

    public Person(){
        this.name="";
        this.id=0;
    }
    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }
}
