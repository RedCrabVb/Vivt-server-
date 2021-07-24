package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGroup;
    private String name_groups;

    public Groups(String name_groups) {
        this.name_groups = name_groups;
    }

    public Groups() {

    }

    public long getIdGroup() {
        return idGroup;
    }

    public String getName_groups() {
        return name_groups;
    }
}
