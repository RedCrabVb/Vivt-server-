package com.dataBase.hibernateDataBase;

import com.dataBase.DataBase;
import com.dataBase.hibernateDataBase.models.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
        configuration.addAnnotatedClass(Lesson.class);
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
    public void registration(String name, String surname, String patronymic, String groupsStr, String mail, String password) {
        int idGroup = getIDGroupForName(groupsStr);

        String grade_book = groupsStr + " - " + new Random().nextInt(); // BAD
        String token = UUID.randomUUID().toString(); //BAD
        Student student = new Student(token, mail, password, surname, name, patronymic, grade_book, idGroup);

        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(student);
        tx1.commit();
        session.close();
    }

    @Override
    public int getIDForToken(String token) {
        List<Student> students =  sessionFactory.openSession().createQuery("From Student").list();

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getToken().equals(token)) {
                return student.getIdStudent();
            }
        }

        throw new IndexOutOfBoundsException("Not found groups");
    }

    @Override
    public JsonObject personData(int ID) {
        Session session = sessionFactory.openSession();
        Student student = session.get(Student.class, ID);

        JsonObject json = new JsonObject();
        json.addProperty("mail", student.getMail());
        json.addProperty("surname", student.getSurname());
        json.addProperty("name", student.getName());
        json.addProperty("patronymic", student.getPatronymic());
        json.addProperty("grade_book_number", student.getGrade_book_number());
        json.addProperty("group", getNameGroupForID(student.getGroups_id()));

        return json;
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        List<Day> days =  sessionFactory.openSession().createQuery("From Day").list();
        JsonObject json = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        json.add("jsonArray", jsonArray);
        days.stream().filter(day -> day.getGroups_idGroup() == 1).forEach(day -> {
            JsonObject obj = JsonParser.parseString(new Gson().toJson(day)).getAsJsonObject();
            jsonArray.add(obj);
        });
        return json;
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
        String query = "From " + Message.class.getName();
        List<Message> messages =  sessionFactory.openSession().createQuery(query).list();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonObject.add("array", jsonArray);
        for (Message message : messages) {
            if (message.getStudent_ID() == ID) {
                jsonArray.add(JsonParser.parseString(new Gson().toJson(message)));
            }
        }

        return jsonObject;
    }

    @Override
    public JsonObject academicPerformance(int ID) throws SQLException {
        return new JsonObject();
    }

    @Override
    public boolean sendMessage(int sender, int recipient, String header, String text) {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(new Message(sender, header, text, recipient));
        tx1.commit();
        session.close();
        return true;
    }

    @Override
    public int getIDbyMail(String mail) {
        String quer = "From " + Student.class.getName();
        List<Student> groups = sessionFactory.openSession().createQuery(quer).list();
        for (Student student : groups) {
            if (student.getMail().equals(mail)) {
                return student.getIdStudent();
            }
        }

        throw new IndexOutOfBoundsException("Not found groups");
    }

    private String getNameGroupForID(int ID) {
        String quer = "From " + Groups.class.getName();
        List<Groups> groups = sessionFactory.openSession().createQuery(quer).list();
        for (Groups group : groups) {
            if (group.getIdGroup() == ID) {
                return  group.getName_groups();
            }
        }

        throw new IndexOutOfBoundsException("Not found groups");
    }

    private int getIDGroupForName(String name) {
        String query = "From " + Groups.class.getName();
        List<Groups> groups = sessionFactory.openSession().createQuery(query).list();
        for (Groups group : groups) {
            if (group.getName_groups().equals(name)) {
                return  (int) group.getIdGroup();
            }
        }

        throw new IndexOutOfBoundsException("Not found groups");
    }
}
