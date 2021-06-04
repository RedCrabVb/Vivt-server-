package DataBase;

import com.google.gson.*;
import com.vivt.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonDataBase implements DataBase {
    private DataBaseJson dataBaseJson;
    private Gson gson = new Gson();

    private DataBaseJson init() {
        DataBaseJson dataBase = new DataBaseJson();

        Group newGroup = new Group("pks-29");
        Group newGroup1 = new Group("d-30");

        Student student = new Student(newGroup.getID(), "mail", "pass", "Alex Alex1 Alex2", "pks-234");
        Student student2 = new Student(newGroup.getID(), "gmail", "pass234", "Pasha Pasha1 Pasha2", "pks-235");
        Student student3 = new Student(newGroup.getID(), "ymail@mail.com", "wrq", "Lera Lera1 Lera2", "pks-236");
        Student student4 = new Student(newGroup.getID(), "gmail", "234", "Ruslan Ruslan1 Ruslan2", "pks-237");
        Student student5 = new Student(newGroup1.getID(), "pass", "pass", "Alex Alex1 Alex2", "d-234");

        dataBase.students.add(student);
        dataBase.students.add(student2);
        dataBase.students.add(student3);
        dataBase.students.add(student4);
        dataBase.students.add(student5);

        dataBase.groups.add(newGroup);
        dataBase.groups.add(newGroup1);

        dataBase.news.add(new News());
        dataBase.news.add(new News());

        dataBase.messages.add(new Message(student.getID(), "Main", "bal_bal_bla 12", student2.getID()));
        dataBase.messages.add(new Message(student.getID(), "LK", "bal_bal_bla 15", student5.getID()));
        dataBase.messages.add(new Message(student2.getID(), "LK", "bal_bal_bla 23", student3.getID()));

        return dataBase;
    }

    public JsonDataBase(Config config) throws FileNotFoundException {
        dataBaseJson = gson.fromJson(new FileReader(config.getPathJsonDataBase()), DataBaseJson.class);
        System.out.println(gson.toJson(init()));
    }
    @Override
    public int realAccount(String login, String password) {
        for(Student elm : dataBaseJson.students) {
            if (elm.equalsLoginPass(login, password)) {
                return elm.getID();
            }
        }

        return -1;
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        for(Student elm : dataBaseJson.students) {
            if (elm.getID() == ID) {
                String jsonStr = gson.toJson(elm);
                return JsonParser.parseString(jsonStr).getAsJsonObject();
            }
        }

        return null;
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        JsonObject result = new JsonObject();
        int groups_ID = 0;
        for (Student elm : dataBaseJson.students) {
            if (elm.getID() == ID) {
                groups_ID = elm.getGroups_ID();
                break;
            }
        }

        JsonElement jsonSchedule = null;
        for (Group group : dataBaseJson.groups) {
            if (group.getID() == groups_ID) {
                jsonSchedule = JsonParser.parseString(gson.toJson(group));
                break;
            }
        }

        result.add("schedule", jsonSchedule);
        return result;
    }

    @Override
    public JsonObject news() throws SQLException {
        String jsonStr =  gson.toJson(dataBaseJson.news);
        var jsonArrNews = JsonParser.parseString(jsonStr).getAsJsonArray();
        JsonObject newJsonObj = new JsonObject();
        newJsonObj.add("news", jsonArrNews);

        return newJsonObj;
    }

    @Override
    public JsonObject importantDates() throws SQLException {
        return null;
    }

    @Override
    public JsonObject message(int ID) throws SQLException {
        JsonArray jsonMsg = new JsonArray();
        for (Message msg : dataBaseJson.messages) {
            if (msg.getID() == ID) {
                jsonMsg.add(JsonParser.parseString(gson.toJson(msg)));
            }
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("msg", jsonMsg);
        return jsonObject;
    }

    @Override
    public JsonObject academicPerformance(int ID) throws SQLException {
        return null;
    }

    @Override
    public int getId(String login) {
        return 0;
    }
}














class DataBaseJson {
    public ArrayList<News> news = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();
    public ArrayList<Group> groups = new ArrayList<>();
}

/*****/

class News {
    private static final AtomicInteger count = new AtomicInteger(0);
    public int ID;
    public String name_news;
    public String text_news;
    public String img_path;

    public News() {
        ID = count.incrementAndGet();
        name_news = "None_name";
        text_news = "None_text";
        img_path = "./img.png";
    }
}

/*****/

class Group {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int ID;
    private String name_groups;
    private Schedule schedule;

    public Group(String name_groups) {
        this.name_groups = name_groups;

        this.ID = count.incrementAndGet();
        this.schedule = new Schedule();
    }

    public int getID() {
        return ID;
    }
}

class Schedule {
    private ArrayList<Day> dayArrayList = new ArrayList<>();
    public Schedule() {
        List<String> week = Arrays.asList("Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun");
        for (String day : week) {
            dayArrayList.add(new Day(day));
        }
    }
}

class Day {
    private String day_of_week;
    private ArrayList<Thing> thingsArrayList = new ArrayList<>();

    public Day(String day_of_week) {
        this.day_of_week = day_of_week;

        String name_things = "";
        String teacher_name = "";
        String audience = "";
        for (int i = 1; i < 8; i++) {
            thingsArrayList.add(new Thing(i, name_things, teacher_name, audience));
        }
    }
}

class Thing {
    private int number_pairs;
    private String name_things;
    private String teacher_name;
    private String audience;

    public Thing(int number_pairs, String name_things, String teacher_name, String audience) {
        this.number_pairs = number_pairs;
        this.name_things = name_things;
        this.teacher_name = teacher_name;
        this.audience = audience;
    }
}

class Student {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int ID;
    private int Groups_ID;
    private String mail;
    private String password;
    private String FIO;
    private String grade_book_number;

    public Student(int Groups_ID, String mail, String password, String FIO, String grade_book_number) {
        this.Groups_ID = Groups_ID;
        this.ID = count.incrementAndGet();
        this.mail = mail;
        this.password = password;
        this.FIO = FIO;
        this.grade_book_number = grade_book_number;
    }

    public boolean equalsLoginPass(String login, String password) {
        return  this.mail.equals(login) && this.password.equals(password);
    }

    public int getID() {
        return this.ID;
    }

    public int getGroups_ID() {return this.Groups_ID;}
}

/*****/

class Message {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int ID;
    private int recipient;
    private int from_whom;
    private Boolean is_read;
    private String header;
    private String text;

    public Message(int recipient, String header, String text, int from_whom) {
        this.ID = count.incrementAndGet();
        this.recipient = recipient;
        this.from_whom = from_whom;
        this.text = text;
        this.header = header;
    }

    public int getID() {
        return this.recipient;
    }
}