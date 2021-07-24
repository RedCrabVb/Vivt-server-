package com.dataBase.hibernateDataBase;

import com.dataBase.DataBase;
import com.dataBase.hibernateDataBase.dao.ScheduleDao;
import com.dataBase.hibernateDataBase.dao.StudentDao;
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
    private StudentDao studentDao;
    private ScheduleDao scheduleDao;

    private void init() {
        //create table in database
        Student student = new Student("asdf243", "testMail", "pass", "test", "test", "test", "test_432", 1);
        Groups group = new Groups("test");

        scheduleDao.createScheduleForGroup(2);
    }

    public MySqlDataBase() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Day.class);
        configuration.addAnnotatedClass(Groups.class);
        configuration.addAnnotatedClass(Message.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Lesson.class);
        configuration.addAnnotatedClass(Thing.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

        studentDao = new StudentDao(sessionFactory);
        scheduleDao = new ScheduleDao(sessionFactory);

        //init();
    }


    @Override
    public String authorization(String login, String password) {
        Student student =  (Student) sessionFactory.openSession()
                .createQuery("From " + Student.class.getName() + " where mail = :login and password = :password")
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();

        return student.getToken();
    }

    @Override
    public void registration(String name, String surname, String patronymic, String groupsStr, String mail, String password) {
        int idGroup = getIDGroupForName(groupsStr);

        String grade_book = groupsStr + " - " + new Random().nextInt(); // BAD
        String token = UUID.randomUUID().toString(); //BAD
        Student student = new Student(token, mail, password, surname, name, patronymic, grade_book, idGroup);

        studentDao.save(student);
    }

    @Override
    public int getIDForToken(String token) {
        return studentDao.getIDForToken(token);
    }

    @Override
    public JsonObject personData(Student _student) {
        Student student = studentDao.findById(_student.getIdStudent());

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
    public JsonObject schedule(Student student) throws SQLException {
        List<Day> days =  sessionFactory.openSession().createQuery("From Day").list();
        JsonObject json = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        json.add("jsonArray", jsonArray);
        days.stream().filter(day -> day.getGroups_idGroup() == student.getGroups_id()).forEach(day -> {
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
    public JsonObject message(Student student) throws SQLException {
        String query = "From " + Message.class.getName();
        List<Message> messages =  sessionFactory.openSession().createQuery(query).list();
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        jsonObject.add("array", jsonArray);
        for (Message message : messages) {
            if (message.getStudent_ID() == student.getIdStudent()) {
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

    @Override
    public Student getStudentForToken(String token) {
        String query = "From " + Student.class.getName() + " where token = :token";
        Student student = (Student) sessionFactory.openSession()
                .createQuery(query)
                .setParameter("token", token)
                .uniqueResult();
        return student;
    }

    private String getNameGroupForID(long id) {
        String query = "From " + Groups.class.getName() + " where idGroup = :idGroup";
        Groups groups = (Groups) sessionFactory.openSession()
                .createQuery(query)
                .setParameter("idGroup", id)
                .uniqueResult();
        return groups.getName_groups();
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
