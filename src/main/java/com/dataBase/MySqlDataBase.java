package com.dataBase;

import com.dataBase.models.Groups;
import com.dataBase.models.Student;
import com.dataBase.models.Teacher;
import com.google.gson.JsonObject;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.List;

public class MySqlDataBase implements DataBase {
    private SessionFactory sessionFactory;

    @Deprecated
    public MySqlDataBase() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Day.class);
        configuration.addAnnotatedClass(Groups.class);
        configuration.addAnnotatedClass(Message.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Teacher.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }


    @Override
    public String authorization(String login, String password) {
        List<Student> students =  sessionFactory.openSession().createQuery("From Student").list();

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getMail().equals(login) && student.getPassword().equals(password)) {
                return student.getToken();
            }
        }

        return null;
    }

    @Override
    public void registration(String name, String surname, String patronymic, String groups, String mail, String password) {
    }

    @Override
    public int getIDForToken(String token) {
        return 0;
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        JsonObject obj = new JsonObject();
        return obj;
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject news() throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject importantDates() throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject message(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public JsonObject academicPerformance(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public boolean sendMessage(int sender, int recipient, String header, String text) {
        return false;
    }

    @Override
    public int getIDbyMail(String name) {
        return 0;
    }
}
