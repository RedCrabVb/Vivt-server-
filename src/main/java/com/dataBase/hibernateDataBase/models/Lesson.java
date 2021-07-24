package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLesson;
    private int number_pairs;
    private int audience;

    @ManyToOne
    @JoinColumn(name = "thing_idThing")
    private Thing thing;

    public Lesson() {

    }
}
