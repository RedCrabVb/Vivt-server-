package com.dataBase.hibernateDataBase.dao;

import com.dataBase.hibernateDataBase.models.Day;
import com.dataBase.hibernateDataBase.models.Lesson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private SessionFactory sessionFactory;

    public ScheduleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createScheduleForGroup(int id) {
        List<Day> days = new ArrayList<>();
        days.add(new Day("Mon", id));
        days.add(new Day("Tue", id));
        days.add(new Day("Wed", id));

        saveList(days);

        List<Day> daysFromDB = sessionFactory.openSession().createQuery("").list();
        List<Lesson> lessons = new ArrayList<>();
        for (Day day : days) {
            //List<Lesson> lessons = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                Lesson lesson = new Lesson(i + 1, 2, day.getIdDay());
                lessons.add(lesson);
            }
            //day.setLesson(lessons);
        }

        saveList(lessons);
    }

    private void saveList(List list) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        list.stream().forEach(key -> {
            session.save(key);
        });
        tx1.commit();
        session.close();
    }
}
