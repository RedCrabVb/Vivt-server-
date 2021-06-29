package com.dataBase.fileDataBase;

import com.dataBase.DataBase;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.*;

public class JsonDataBase implements DataBase {
    private static String pathJsonDataBase;
    private DataInJsonFormat dataInJsonFormat;
    private Gson gson = new Gson();

    private DataInJsonFormat init() {
        DataInJsonFormat dataBase = new DataInJsonFormat();

        Group newGroup = new Group("pks-029");
        Group newGroup1 = new Group("at-028");

        Student student = new Student(newGroup.getID(), "mail", "pass", "Иванов", "Иван", "Иванович", "pks-234");
        Student student2 = new Student(newGroup.getID(), "gmail", "wrq", "Сергей", "Федоров", "Федорович", "pks-234");
        Student student3 = new Student(newGroup.getID(), "ymail@mail", "wrq123", "Карсева", "Полина", "Алексеевна", "pks-234");
        Student student4 = new Student(newGroup.getID(), "gmail22", "wrq", "Логинов", "Сергей", "Николаевич", "pks-234");
        Student student5 = new Student(newGroup.getID(), "gmail22", "wrq", "Владимирова", "Ольга", "Викторовна", "at-028");

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

    public void save() {

        try (FileWriter outputStream = new FileWriter(JsonDataBase.pathJsonDataBase)) {
            ;
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String str = gson.toJson(dataInJsonFormat);
            outputStream.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonDataBase(String pathJsonDataBase) throws FileNotFoundException {
        //dataInJsonFormat = init();
        dataInJsonFormat = gson.fromJson(new FileReader(pathJsonDataBase), DataInJsonFormat.class);
        JsonDataBase.pathJsonDataBase = pathJsonDataBase;
    }

    @Override
    public String authorization(String login, String password) throws Exception {
        for (Student elm : dataInJsonFormat.students) {
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
        for (Student elm : dataInJsonFormat.students) {
            if (elm.getToken().equals(token)) {
                return elm.getID();
            }
        }

        throw new IllegalArgumentException("Not found for 'token'");
    }

    @Override
    public JsonObject personData(int ID) throws SQLException {
        for (Student elm : dataInJsonFormat.students) {
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
        String jsonStr = gson.toJson(dataInJsonFormat.news);
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

    @Override
    public boolean sendMessage(int sender, int recipient, String header, String text) {
        boolean result = dataInJsonFormat.messages.add(new Message(recipient, header, text, sender));
        save();
        return result;
    }

    @Override
    public int getIDbyMail(String mail) {
        for (Student elm : dataInJsonFormat.students) {
            if (elm.getMail().equals(mail)) {
                return elm.getID();
            }
        }

        throw new IllegalArgumentException("Not found user");
    }
}
