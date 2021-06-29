package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTeacher;
    private String surname;
    private String name;
    private String patronymic;

    public Teacher(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }
}
