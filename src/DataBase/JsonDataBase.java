package DataBase;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonDataBase implements DataBase {
    private DataInJsonFormat dataInJsonFormat;
    private Gson gson = new Gson();

    private DataInJsonFormat init() {
        DataInJsonFormat dataBase = new DataInJsonFormat();

        Group newGroup = new Group("pks-029");
        Group newGroup1 = new Group("at-028");

        Student student = new Student(newGroup.getID(), "mail", "pass", "Иванов", "Иван", "Иванович", "pks-234");
        Student student2 =  new Student(newGroup.getID(), "gmail", "wrq", "Сергей", "Федоров", "Федорович", "pks-234");
        Student student3 =  new Student(newGroup.getID(), "ymail@mail", "wrq123", "Карсева", "Полина", "Алексеевна", "pks-234");
        Student student4 =  new Student(newGroup.getID(), "gmail22", "wrq", "Логинов", "Сергей", "Николаевич", "pks-234");
        Student student5 =  new Student(newGroup.getID(), "gmail22", "wrq", "Владимирова", "Ольга", "Викторовна", "at-028");

        dataBase.teachers.add(new Teacher("", "", ""));
        dataBase.teachers.add(new Teacher("Карсева", "Юлия", "Иванова"));
        dataBase.teachers.add(new Teacher("Ардаков", "Игорь", "Герасимович"));
        dataBase.teachers.add(new Teacher("Гришина", "Ольга", "Константиновна"));


        dataBase.things.add(new Thing("", ""));
        dataBase.things.add(new Thing("L", "Физическая культура"));
        dataBase.things.add(new Thing("P", "Технические средства информатизации"));
        dataBase.things.add(new Thing("L", "Иностранный язык"));
        dataBase.things.add(new Thing("P", "Операционные системы"));


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

    private void save() {
        System.out.println(gson.toJson(dataInJsonFormat));
    }

    public JsonDataBase(String pathJsonDataBase) throws FileNotFoundException {
       // dataInJsonFormat = init();
        dataInJsonFormat = gson.fromJson(new FileReader(pathJsonDataBase), DataInJsonFormat.class);
        //save();
    }
    @Override
    public String authorization(String login, String password) throws Exception {
        for(Student elm : dataInJsonFormat.students) {
            if (elm.equalsLoginPass(login, password)) {
                return elm.getToken();
            }
        }

        throw new IllegalArgumentException("Not found for 'token'");
    }

    @Override
    public void registration(String name, String surname, String patronymic, String groups, String mail, String password) {
        Group groupThis = null;
        for (Group group : dataInJsonFormat.groups) {
            if (group.getName_groups().equals(groups)) {
                groupThis = group;
            }
        }

        int groups_ID = groupThis.getID();
        String grab_book_number = groups + "2" + new Random().nextLong();
        Student student = new Student(groups_ID, mail, password, surname,
                name, patronymic, grab_book_number);

        dataInJsonFormat.students.add(student);
    }

    @Override
    public int getIDForToken(String token) {
        for(Student elm : dataInJsonFormat.students) {
            if (elm.getToken().equals(token)) {
                return elm.getID();
            }
        }

        throw new IllegalArgumentException("Not found for 'token'");
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        for(Student elm : dataInJsonFormat.students) {
            if (elm.getID() == ID) {
                String jsonStr = gson.toJson(elm);
                return JsonParser.parseString(jsonStr).getAsJsonObject();
            }
        }

        throw new IllegalArgumentException("Not found for 'token'");
    }

    @Override
    public JsonObject schedule(int ID) throws SQLException {
        int groups_ID = 0;
        for (Student elm : dataInJsonFormat.students) {
            if (elm.getID() == ID) {
                groups_ID = elm.getGroups_ID();
                break;
            }
        }

        for (Group group : dataInJsonFormat.groups) {
            if (group.getID() == groups_ID) {
                return group.getSchedule().toJson(dataInJsonFormat);
            }
        }

        throw new IllegalArgumentException("Not found for 'token'");
    }

    @Override
    public JsonObject news() throws SQLException {
        String jsonStr =  gson.toJson(dataInJsonFormat.news);
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
        for (Message msg : dataInJsonFormat.messages) {
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
}














class DataInJsonFormat {
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

        return  new Thing("D", "S");
    }

    public Teacher getTeacher(int ID) {
        for (Teacher teacher : teachers) {
            if (teacher.getID() == ID) {
                return teacher;
            }
        }

        return  new Teacher("", "", "");
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

    public int getID() {return  ID;}

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
        return  this.mail.equals(login) && this.password.equals(password);
    }

    public int getID() {
        return this.ID;
    }

    public int getGroups_ID() {return this.Groups_ID;}

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