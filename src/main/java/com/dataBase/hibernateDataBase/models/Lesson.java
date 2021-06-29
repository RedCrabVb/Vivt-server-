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
    private long day_idDay;

    public Lesson() {

    }

    public Lesson(int number_pairs, int audience, long day_idDay) {
        this.number_pairs = number_pairs;
        this.audience = audience;
        this.day_idDay = day_idDay;
    }
}
