package com.app.splitter.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_group") // Adjust the table name as needed
public class PersonGroup {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name") // Renamed from "group_name" to "name"
    private String name;

    // Constructors, getters, setters, and other methods

    // Default constructor
    public PersonGroup() {
        this.id = 0L;
        this.name = "";
    }

    // Parameterized constructor
    public PersonGroup(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and Setter for id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
