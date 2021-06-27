package com.dataBase.models;

import javax.persistence.*;

@Entity
@Table(name = "Day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDay;
    private String day_of_week;
    private long groups_idGroup;

    public Day(String day_of_week, long groups_idGroup) {
        this.day_of_week = day_of_week;
        this.groups_idGroup = groups_idGroup;
    }
}