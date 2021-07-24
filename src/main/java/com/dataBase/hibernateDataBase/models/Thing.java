package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Thing")
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idThing;
    private String type_lesson;
    private String name_things;
    private int teacher_idTeacher;

    public Thing() {

    }

    public long getIdThing() {
        return idThing;
    }

    public String getType_lesson() {
        return type_lesson;
    }

    public String getName_things() {
        return name_things;
    }

    public int getTeacher_idTeacher() {
        return teacher_idTeacher;
    }
}
