package com.app.splitter.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="person_to_group")
public class GroupMapping {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name="person_id")
    private int personId;
    @Column(name="group_id")
    private int groupId;

    public GroupMapping(){

    }

    public GroupMapping(int personId, int groupId) {
        this.id = String.valueOf(personId)+String.valueOf(groupId);
        this.personId = personId;
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
