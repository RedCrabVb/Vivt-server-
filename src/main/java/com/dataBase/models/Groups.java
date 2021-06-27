package com.dataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGroup;
    private String name_groups;

    public Groups() {
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(long id) {
        this.idGroup = id;
    }

    public String getName_groups() {
        return name_groups;
    }

    public void setName_groups(String name_groups) {
        this.name_groups = name_groups;
    }
}
