package com.dataBase.models;

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
    private int lesson_idLesson;

    public Thing(String type_lesson, String name_things, int teacher_idTeacher, int lesson_idLesson) {
        this.type_lesson = type_lesson;
        this.name_things = name_things;
        this.teacher_idTeacher = teacher_idTeacher;
        this.lesson_idLesson = lesson_idLesson;
    }
}
