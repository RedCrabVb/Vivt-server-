package com.dataBase.hibernateDataBase.dao;

import com.dataBase.hibernateDataBase.models.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class StudentDao {
    private SessionFactory sessionFactory;

    public StudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(student);
        tx1.commit();
        session.close();
    }

    public Student findById(int id) {
        return sessionFactory.openSession().get(Student.class, id);
    }

    public int getIDForToken(String token) {
        String query = "From " + Student.class.getName() + " where token = :token";
        Student student = (Student) sessionFactory.openSession().
                createQuery(query)
                .setParameter("token", token)
                .uniqueResult();
        return student.getIdStudent();
    }

}
