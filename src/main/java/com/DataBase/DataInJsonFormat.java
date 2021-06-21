package com.DataBase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DataInJsonFormat {
    public ArrayList<News> news = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public ArrayList<Thing> things = new ArrayList<>();
    public ArrayList<Message> messages = new ArrayList<>();
    public ArrayList<Group> groups = new ArrayList<>();

    public Thing getThings(int ID) {
        for (Thing thing : things) {
            if (thing.getID() == ID) {
                return thing;
            }
        }

        return new Thing("D", "S");
    }

    public Teacher getTeacher(int ID) {
        for (Teacher teacher : teachers) {
            if (teacher.getID() == ID) {
                return teacher;
            }
        }

        return new Teacher("", "", "");
    }
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

    public String getName_groups() {
        return name_groups;
    }

    public Schedule getSchedule() {
        return schedule;
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

    public JsonObject toJson(DataInJsonFormat dataBase) {
        JsonArray jsonArr = JsonParser.parseString(new Gson().toJson(dayArrayList)).getAsJsonArray();

        for (int i = 0; i < jsonArr.size(); i++) {
            JsonArray jsonArr2 = jsonArr.get(i).getAsJsonObject().getAsJsonArray("thingsArrayList");
            for (int j = 0; j < jsonArr2.size(); j++) {
                int teacher_ID = jsonArr2.get(j).getAsJsonObject().get("teacher_ID").getAsInt();
                int things_ID = jsonArr2.get(j).getAsJsonObject().get("things_ID").getAsInt();

                jsonArr2.get(j).getAsJsonObject().remove("teacher_ID");
                jsonArr2.get(j).getAsJsonObject().remove("things_ID");

                jsonArr2.get(j).getAsJsonObject().addProperty("type", dataBase.getThings(things_ID).getType_lesson());
                jsonArr2.get(j).getAsJsonObject().addProperty("things", dataBase.getThings(things_ID).getName_things());
                jsonArr2.get(j).getAsJsonObject().addProperty("teacher", dataBase.getTeacher(teacher_ID).getFullName());

            }
        }

        JsonObject jsonObj = new JsonObject();
        jsonObj.add("schedule", jsonArr);

        return jsonObj;
    }
}

class Day {
    private String day_of_week;
    private ArrayList<Lesson> thingsArrayList = new ArrayList<>();

    public Day(String day_of_week) {
        this.day_of_week = day_of_week;

        String audience = "";
        for (int i = 1; i < 8; i++) {
            thingsArrayList.add(new Lesson(i, 0, audience, 0));
        }
    }
}

class Lesson {
    private int number_pairs;
    private int teacher_ID;
    private String audience;
    private int things_ID;

    public Lesson(int number_pairs, int teacher_ID, String audience, int things_ID) {
        this.number_pairs = number_pairs;
        this.teacher_ID = teacher_ID;
        this.audience = audience;
        this.things_ID = things_ID;
    }
}

class Teacher {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int ID;
    private String surname;
    private String name;
    private String patronymic;

    Teacher(String surname, String name, String patronymics) {
        this.ID = count.incrementAndGet() - 1;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymics;
    }

    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }

    public int getID() {
        return ID;
    }
}

class Thing {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int ID;
    private String type_lesson;
    private String name_things;

    Thing(String type_lesson, String name_things) {
        this.ID = count.incrementAndGet() - 1;
        this.name_things = name_things;
        this.type_lesson = type_lesson;
    }

    public int getID() {
        return ID;
    }

    public String getType_lesson() {
        return type_lesson;
    }

    public String getName_things() {
        return name_things;
    }
}

class Student {
    private static final AtomicInteger count = new AtomicInteger(0);
    private static List<String> tokenList = new ArrayList<>();
    private int ID;
    private int Groups_ID;
    private String token;

    private String mail;
    private String password;

    private String surname;
    private String name;
    private String patronymic;

    private String grade_book_number;

    public Student(int Groups_ID, String mail, String password, String surname, String name, String patronymics, String grade_book_number) {
        this.Groups_ID = Groups_ID;
        this.ID = count.incrementAndGet();
        this.token = generatedToken();
        this.mail = mail;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymics;
        this.grade_book_number = grade_book_number;
    }

    public boolean equalsLoginPass(String login, String password) {
        return this.mail.equals(login) && this.password.equals(password);
    }

    public int getID() {
        return this.ID;
    }

    public int getGroups_ID() {
        return this.Groups_ID;
    }

    private String generatedToken() {
        String generatedString = "test";
        do {
            generatedString = UUID.randomUUID().toString();
        } while (tokenList.contains(generatedString));
        tokenList.add(generatedString);

        return generatedString;
    }

    public String getToken() {
        return token;
    }

    public String getMail() {
        return mail;
    }
}

/*****/

class Message {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int recipient;
    private int from_whom;
    private Boolean is_read;
    private String header;
    private String text;

    public Message(int recipient, String header, String text, int from_whom) {
        this.recipient = recipient;
        this.from_whom = from_whom;
        this.text = text;
        this.header = header;
    }

    public int getID() {
        return this.recipient;
    }
}