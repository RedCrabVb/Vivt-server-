package com.dataBase.hibernateDataBase.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDay;
    private String day_of_week;
    private long groups_idGroup;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "day_idDay")
    private List<Lesson> lesson;

    public Day() {

    }

    public Day(String day_of_week, long groups_idGroup) {
        this.day_of_week = day_of_week;
        this.groups_idGroup = groups_idGroup;
    }

    public List<Lesson> getLesson() {
        return lesson;
    }

    public long getGroups_idGroup() {
        return groups_idGroup;
    }

    public long getIdDay() {
        return idDay;
    }
}